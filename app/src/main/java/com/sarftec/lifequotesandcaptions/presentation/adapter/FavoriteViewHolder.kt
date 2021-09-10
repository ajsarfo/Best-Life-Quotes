package com.sarftec.lifequotesandcaptions.presentation.adapter

import com.sarftec.lifequotesandcaptions.databinding.LayoutQuoteBinding
import com.sarftec.lifequotesandcaptions.model.Quote
import com.sarftec.lifequotesandcaptions.presentation.binding.FavoriteBinding

class FavoriteViewHolder(
    layoutBinding: LayoutQuoteBinding,
    dependency: QuoteAdapter.AdapterDependency,
    private val onDelete: (Quote) -> Unit
) : QuoteViewHolder(layoutBinding, dependency) {

    override fun bind(quote: Quote) {
        layoutBinding.binding = FavoriteBinding(adapterDependency, quote, onDelete)
        layoutBinding.executePendingBindings()
    }
}