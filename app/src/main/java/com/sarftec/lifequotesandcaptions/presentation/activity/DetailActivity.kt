package com.sarftec.lifequotesandcaptions.presentation.activity

import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.appodeal.ads.Appodeal
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import com.sarftec.lifequotesandcaptions.R
import com.sarftec.lifequotesandcaptions.advertisement.InterstitialManager
import com.sarftec.lifequotesandcaptions.databinding.ActivityDetailBinding
import com.sarftec.lifequotesandcaptions.databinding.LayoutTextPanelBinding
import com.sarftec.lifequotesandcaptions.editSettings
import com.sarftec.lifequotesandcaptions.parcel.QuoteToDetail
import com.sarftec.lifequotesandcaptions.permission.ImageHandler
import com.sarftec.lifequotesandcaptions.permission.ReadWriteHandler
import com.sarftec.lifequotesandcaptions.presentation.adapter.DetailImageAdapter
import com.sarftec.lifequotesandcaptions.presentation.adapter.DetailPagerAdapter
import com.sarftec.lifequotesandcaptions.presentation.binding.ShareDialogBinding
import com.sarftec.lifequotesandcaptions.presentation.dialog.ImageChooserDialog
import com.sarftec.lifequotesandcaptions.presentation.dialog.ShareDialog
import com.sarftec.lifequotesandcaptions.presentation.image.ImageHolder
import com.sarftec.lifequotesandcaptions.presentation.panel.TextPanelManager
import com.sarftec.lifequotesandcaptions.presentation.utils.*
import com.sarftec.lifequotesandcaptions.presentation.viewmodel.*
import com.sarftec.lifequotesandcaptions.readSettings
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first

@AndroidEntryPoint
class DetailActivity : BaseActivity(), ColorPickerDialogListener {

    private val layoutBinding by lazy {
        ActivityDetailBinding.inflate(
            layoutInflater
        )
    }

    private val interstitialManager by lazy {
        InterstitialManager(
            this,
            networkManager,
            listOf(1, 3)
        )
    }

    private val pagerAdapter by lazy {
        DetailPagerAdapter {
            if (viewModel.isPanelShown()) viewModel.toolbarIconClicked()
            else viewModel.randomBackground()
        }
    }

    private lateinit var readWriteHandler: ReadWriteHandler

    private lateinit var imageHandler: ImageHandler

    private val textPanelManager by lazy {
        TextPanelManager(
            lifecycleScope,
            viewModel,
            LayoutTextPanelBinding.inflate(
                layoutInflater,
                layoutBinding.textPanelContainer,
                true
            )
        )
    }

    private val shareDialog by lazy {
        ShareDialog(
            layoutBinding.root,
            ShareDialogBinding(
                "Share As",
                "Text",
                "Image",
                onOption1 = {
                    viewModel.getCurrentQuote()?.let {
                        share(it.quote, "Share")
                    }
                },
                onOption2 = {
                    interstitialManager.showAd {
                        shareBackgroundImage()
                    }
                }
            )
        )
    }

    private val imageAdapter by lazy {
        DetailImageAdapter(dependency) {
            viewModel.setBackgroundImage(it)
            closeImageChooser()
        }
    }

    private val imageChooserDialog by lazy {
        ImageChooserDialog(layoutBinding.root, imageAdapter)
    }

    private val viewModel by viewModels<DetailViewModel>()

    override fun onResume() {
        super.onResume()
        Appodeal.show(this, Appodeal.BANNER_VIEW)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)
        Appodeal.setBannerViewId(R.id.main_banner)
        imageStore.reload()
        savedInstanceState ?: kotlin.run {
            intent?.getParcelableExtra<QuoteToDetail>(ACTIVITY_PARCEL)?.let {
                viewModel.setParcel(it)
            }
        }
        configureHandlers()
        configureActivity()
        configureViewPager()
        configureLayout()
        viewModel.fetch()
        viewModel.toolbarState.observe(this) { state ->
            when (state) {
                ToolbarState.EDIT -> {
                    layoutBinding.editIcon.setImageResource(R.drawable.ic_close)
                    textPanelManager.show()
                }
                else -> {
                    layoutBinding.editIcon.setImageResource(R.drawable.ic_edit)
                    textPanelManager.dismiss()
                }
            }
        }

        viewModel.panelState.observe(this) { state ->
            if (state.opacity != -1) layoutBinding.detailUpper.view.setBackgroundColor(state.opacity)
            makeBackgroundChanges(state.backgroundOption)
            pagerAdapter.changePanelState(state)
        }

        viewModel.background.observe(this) { state ->
            state.color?.let { color ->
                layoutBinding.detailUpper.background.setImageDrawable(
                    ColorDrawable(color)
                )
            }
            state.image?.let {
                val uri = (Uri.parse(it))
                layoutBinding.detailUpper.background.glideLoad(
                    if (state.isImageAsset) ImageHolder.AssetImage(uri)
                    else ImageHolder.FileImage(uri)
                )
            }
        }
        viewModel.currentQuote.observe(this) {
            layoutBinding.detailLower.ivLike.setImageResource(
                if (it.isFavorite) R.drawable.ic_like_red else R.drawable.ic_like
            )
        }
        viewModel.indexedQuotes.observe(this) {
            pagerAdapter.submitData(it.quotes)
            layoutBinding.detailUpper.pager.setCurrentItem(it.index, false)
        }
    }

    //Trivial
    private fun closeImageChooser() {
        imageChooserDialog.dismiss()
    }

    private fun configureActivity() {
        lifecycleScope.launchWhenCreated {
            val count = readSettings(ENTRANCE_COUNT, 0).first()
            if (count < 2) {
                editSettings(ENTRANCE_COUNT, count + 1)
                toast("TAP to change image")
            }
        }
        layoutBinding.toolbarTitle.text = viewModel.getToolbarTitle()
        layoutBinding.toolbar.setNavigationOnClickListener { onBackPressed() }
        layoutBinding.edit.setOnClickListener {
            viewModel.toolbarIconClicked()
            //chooseDialog.show()
        }
    }


    private fun configureHandlers() {
        readWriteHandler = ReadWriteHandler(this)
        imageHandler = ImageHandler(this)
    }

    private fun configureViewPager() {
        layoutBinding.detailUpper.pager.adapter = pagerAdapter
        layoutBinding.detailUpper.pager.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    viewModel.setCurrentQuoteIndex(position)
                    super.onPageSelected(position)
                }
            }
        )
    }

    private fun makeBackgroundChanges(option: BackgroundOption) {
        when (option) {
            BackgroundOption.COLOR -> {
                ColorPickerDialog
                    .newBuilder()
                    .show(this@DetailActivity)
                viewModel.neutralizeBackgroundOption()
            }
            BackgroundOption.GALLERY -> {
                imageHandler.pickImageFromGallery { isSuccess, uri ->
                    if (isSuccess && uri != null) viewModel.setBackgroundImage(
                        ImageHolder.FileImage(
                            uri
                        )
                    )
                }
                viewModel.neutralizeBackgroundOption()
            }
            BackgroundOption.IMAGES -> {
                imageChooserDialog.show()
                viewModel.neutralizeBackgroundOption()
            }
            else -> {
                //Nothing
            }
        }
    }

    private fun saveBackgroundImage() {
        readWriteHandler.requestReadWrite {
            layoutBinding.detailUpper.captureFrame.toBitmap { bitmap ->
                saveBitmapToGallery(bitmap)?.let { imageUri ->
                    toast("Saved to gallery")
                    viewInGallery(imageUri)
                }
            }
        }
    }

    private fun shareBackgroundImage() {
        layoutBinding.detailUpper.captureFrame.toBitmap {
            shareImage(it)
        }
    }

    private fun configureLayout() {
        layoutBinding.detailLower.apply {
            ivShare.setOnClickListener {
                shareDialog.show()
            }
            ivCopy.setOnClickListener {
                viewModel.getCurrentQuote()?.let {
                    copy(it.quote)
                    toast("Copied to clipboard")
                }
            }
            ivLike.setOnClickListener {
                viewModel.changeCurrentQuoteFavorite()
            }

            IvSave.setOnClickListener {
                saveBackgroundImage()
            }
        }
    }

    /**
     * Jaredrummler color picker listeners
     */
    override fun onColorSelected(dialogId: Int, color: Int) {
        viewModel.setBackgroundColor(color)
    }

    override fun onDialogDismissed(dialogId: Int) {

    }

    companion object {
        val ENTRANCE_COUNT = intPreferencesKey("detail_entrance_time")
    }
}