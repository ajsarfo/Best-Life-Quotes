package com.sarftec.lifequotesandcaptions.presentation.activity

import android.os.Bundle
import androidx.activity.viewModels
import com.sarftec.lifequotesandcaptions.advertisement.InterstitialManager
import com.sarftec.lifequotesandcaptions.parcel.CategoryToQuote
import com.sarftec.lifequotesandcaptions.parcel.QuoteToDetail
import com.sarftec.lifequotesandcaptions.presentation.adapter.QuoteAdapter
import com.sarftec.lifequotesandcaptions.presentation.viewmodel.QuoteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuoteActivity : BaseQuoteActivity() {

    override val viewModel by viewModels<QuoteViewModel>()

    private val interstitialManager by lazy {
        InterstitialManager(
            this,
            networkManager,
            listOf(2, 3, 3)
        )
    }

    override val quoteAdapter by lazy {
        QuoteAdapter(dependency, viewModel) { quote ->
            interstitialManager.showAd {
                navigateTo(
                    DetailActivity::class.java,
                    parcel = QuoteToDetail(
                        quote.id,
                        quote.categoryId,
                        viewModel.getToolbarTitle() ?: "",
                        viewModel.getSeed(),
                        QuoteToDetail.ORIGIN_QUOTE
                    )
                )
            }
        }
    }

    override fun runInit(savedInstanceState: Bundle?) {
        savedInstanceState ?: kotlin.run {
            intent?.getParcelableExtra<CategoryToQuote>(ACTIVITY_PARCEL)?.let {
                viewModel.setParcel(it)
            }
        }
    }

    override fun runOnResume() {
        quoteAdapter.resetQuoteFavorites(modifiedQuoteList.entries)
        modifiedQuoteList.clear()
    }
}