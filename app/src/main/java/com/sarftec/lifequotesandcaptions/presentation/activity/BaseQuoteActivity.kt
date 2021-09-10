package com.sarftec.lifequotesandcaptions.presentation.activity

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.appodeal.ads.Appodeal
import com.sarftec.lifequotesandcaptions.R
import com.sarftec.lifequotesandcaptions.databinding.ActivityQuoteBinding
import com.sarftec.lifequotesandcaptions.presentation.adapter.ItemDecorator
import com.sarftec.lifequotesandcaptions.presentation.adapter.QuoteAdapter
import com.sarftec.lifequotesandcaptions.presentation.image.ImageHolder
import com.sarftec.lifequotesandcaptions.presentation.utils.glideLoad
import com.sarftec.lifequotesandcaptions.presentation.viewmodel.BaseQuoteViewModel

abstract class BaseQuoteActivity : BaseActivity() {

    protected val layoutBinding: ActivityQuoteBinding by lazy {
        ActivityQuoteBinding.inflate(layoutInflater)
    }

    protected abstract val viewModel: BaseQuoteViewModel

    protected abstract val quoteAdapter: QuoteAdapter

    protected abstract fun runInit(savedInstanceState: Bundle?)

    protected abstract fun runOnResume()

    override fun onResume() {
        super.onResume()
        Appodeal.show(this, Appodeal.BANNER_VIEW)
        runOnResume()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutBinding.root)
        Appodeal.setBannerViewId(R.id.main_banner)
        imageStore.reload()
        runInit(savedInstanceState)
        configureActivity()
        configureAdapter()
        viewModel.fetch()
        viewModel.quote.observe(this) {
            quoteAdapter.submitData(it)
        }
    }

    private fun configureActivity() {
        layoutBinding.toolbar.setNavigationOnClickListener { onBackPressed() }
        layoutBinding.toolbarTitle.text = viewModel.getToolbarTitle()
        layoutBinding.backgroundImage.glideLoad(
            imageStore.getQuoteActivityBackground()
        )
    }

    private fun configureAdapter() {
        layoutBinding.recyclerView.apply {
            adapter = quoteAdapter
            layoutManager = LinearLayoutManager(this@BaseQuoteActivity)
            addItemDecoration(ItemDecorator(12f, this@BaseQuoteActivity))
            setHasFixedSize(false)
        }
    }
}