package com.sarftec.lifequotesandcaptions.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.sarftec.lifequotesandcaptions.R
import com.sarftec.lifequotesandcaptions.advertisement.AdCountManager
import com.sarftec.lifequotesandcaptions.advertisement.BannerManager
import com.sarftec.lifequotesandcaptions.parcel.QuoteToDetail
import com.sarftec.lifequotesandcaptions.presentation.adapter.FavoriteAdapter
import com.sarftec.lifequotesandcaptions.presentation.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : BaseQuoteActivity() {

    override val viewModel by viewModels<FavoriteViewModel>()

    override val quoteAdapter by lazy {
        FavoriteAdapter(dependency, viewModel) { quote ->
            interstitialManager?.showAd {
                navigateTo(
                    DetailActivity::class.java,
                    parcel = QuoteToDetail(
                        quote.id,
                        quote.categoryId,
                        "Favorite Quotes",
                        0,
                        QuoteToDetail.ORIGIN_FAVORITE
                    )
                )
            }
        }
    }

    override fun createAdCounterManager(): AdCountManager {
        return AdCountManager(listOf(3, 2))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*************** Admob Configuration ********************/
        BannerManager(this, adRequestBuilder).attachBannerAd(
            getString(R.string.admob_banner_favorite),
            layoutBinding.mainBanner
        )
        /**********************************************************/
    }

    override fun runInit(savedInstanceState: Bundle?) {

    }

    override fun runOnResume() {
        viewModel.resetQuoteFavorites()
    }
}