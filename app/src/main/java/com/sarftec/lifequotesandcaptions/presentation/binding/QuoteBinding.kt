package com.sarftec.lifequotesandcaptions.presentation.binding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sarftec.lifequotesandcaptions.R
import com.sarftec.lifequotesandcaptions.BR
import com.sarftec.lifequotesandcaptions.model.Quote
import com.sarftec.lifequotesandcaptions.presentation.adapter.QuoteAdapter
import com.sarftec.lifequotesandcaptions.presentation.utils.bindable
import com.sarftec.lifequotesandcaptions.presentation.utils.copy
import com.sarftec.lifequotesandcaptions.presentation.utils.share
import com.sarftec.lifequotesandcaptions.presentation.utils.toast

open class QuoteBinding(
    protected val adapterDependency: QuoteAdapter.AdapterDependency,
    val quote: Quote
) : BaseObservable() {

    @get:Bindable
    var favoriteIcon by bindable(retrieveFavoriteIcon(quote), BR.favoriteIcon)

    private fun retrieveFavoriteIcon(quote: Quote) : Int {
        return if(quote.isFavorite) R.drawable.ic_like_red else R.drawable.ic_like
    }

    fun clicked() {
        adapterDependency.onClick(quote)
    }

    fun onCopy() {
        adapterDependency.dependency.context.apply {
            copy(quote.quote)
            toast("Copied to clipboard")
        }
    }

    fun onShare() {
        adapterDependency.dependency.context.apply {
            share(quote.quote, "Share")
        }
    }

    open fun onFavorite() {
        quote.isFavorite = !quote.isFavorite
        favoriteIcon = retrieveFavoriteIcon(quote)
        adapterDependency.viewModel.saveQuote(quote)
    }
}