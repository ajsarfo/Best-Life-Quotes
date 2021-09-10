package com.sarftec.lifequotesandcaptions.presentation.binding

import com.sarftec.lifequotesandcaptions.model.Quote
import com.sarftec.lifequotesandcaptions.presentation.adapter.QuoteAdapter

class FavoriteBinding(
    adapterDependency: QuoteAdapter.AdapterDependency,
    quote: Quote,
    private val onDelete: (Quote) -> Unit
) : QuoteBinding(adapterDependency, quote) {

    override fun onFavorite() {
        onDelete(quote)
        adapterDependency.viewModel.saveAndRemove(quote)
    }
}