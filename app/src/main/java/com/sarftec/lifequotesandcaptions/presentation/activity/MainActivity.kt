package com.sarftec.lifequotesandcaptions.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.sarftec.lifequotesandcaptions.R
import com.sarftec.lifequotesandcaptions.advertisement.AdCountManager
import com.sarftec.lifequotesandcaptions.advertisement.BannerManager
import com.sarftec.lifequotesandcaptions.databinding.ActivityMainBinding
import com.sarftec.lifequotesandcaptions.manager.AppReviewManager
import com.sarftec.lifequotesandcaptions.parcel.CategoryToQuote
import com.sarftec.lifequotesandcaptions.parcel.QuoteToDetail
import com.sarftec.lifequotesandcaptions.presentation.adapter.CategoryAdapter
import com.sarftec.lifequotesandcaptions.presentation.utils.rateApp
import com.sarftec.lifequotesandcaptions.presentation.utils.share
import com.sarftec.lifequotesandcaptions.presentation.viewmodel.CategoryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(
            layoutInflater
        )
    }

    private val categoryAdapter by lazy {
        CategoryAdapter(dependency) {
            interstitialManager?.showAd {
                navigateTo(
                    QuoteActivity::class.java,
                    parcel = CategoryToQuote(it.id, it.name)
                )
            }
        }
    }

    private val appRatingsManager by lazy {
        AppReviewManager(this)
    }

    private val viewModel by viewModels<CategoryViewModel>()

    override fun createAdCounterManager(): AdCountManager {
        return AdCountManager(listOf(1, 3, 4, 3))
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onResume() {
        super.onResume()
        modifiedQuoteList.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /*************** Admob Configuration ********************/
        BannerManager(this, adRequestBuilder).attachBannerAd(
            getString(R.string.admob_banner_main),
            binding.mainBanner
        )
        /**********************************************************/
        setupRecyclerView()
        configureToolbar()
        viewModel.fetch()
        imageStore.reload()
        viewModel.categories.observe(this) {
            categoryAdapter.submitData(it)
        }
        lifecycleScope.launchWhenCreated {
            appRatingsManager.triggerReview()
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            setHasFixedSize(true)
        }
    }

    private fun configureToolbar() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.favorites -> {
                    navigateTo(FavoriteActivity::class.java)
                    true
                }
                R.id.quote_of_the_day -> {
                    interstitialManager?.showAd {
                        navigateTo(
                            DetailActivity::class.java,
                            parcel = QuoteToDetail(
                                origin = QuoteToDetail.ORIGIN_MAIN,
                                categoryTitle = "Quote Of The Day"
                            )
                        )
                    }
                    true
                }
                R.id.share_app -> {
                    share(
                        getString(R.string.app_share_message),
                        "Share"
                    )
                    true
                }
                R.id.privacy_policy -> {
                    //Open link to privacy policy
                    true
                }
                R.id.rate -> {
                    rateApp()
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}