package com.sarftec.lifequotesandcaptions.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.sarftec.lifequotesandcaptions.advertisement.InterstitialManager
import com.sarftec.lifequotesandcaptions.parcel.QuoteToDetail
import com.sarftec.lifequotesandcaptions.presentation.adapter.FavoriteAdapter
import com.sarftec.lifequotesandcaptions.presentation.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteActivity : BaseQuoteActivity() {

    override val viewModel by viewModels<FavoriteViewModel>()

    private val interstitialManager by lazy {
        InterstitialManager(
            this,
            networkManager,
            listOf(3, 2)
        )
    }
    override val quoteAdapter by lazy {
        FavoriteAdapter(dependency, viewModel) { quote ->
            interstitialManager.showAd {
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

    override fun runInit(savedInstanceState: Bundle?) {

    }

    override fun runOnResume() {
        viewModel.resetQuoteFavorites()
    }
}