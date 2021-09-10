package com.sarftec.lifequotesandcaptions.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifequotesandcaptions.databinding.LayoutQuoteBinding
import com.sarftec.lifequotesandcaptions.model.Quote
import com.sarftec.lifequotesandcaptions.presentation.binding.QuoteBinding

open class QuoteViewHolder(
    protected val layoutBinding: LayoutQuoteBinding,
    protected val adapterDependency: QuoteAdapter.AdapterDependency,
) : RecyclerView.ViewHolder(layoutBinding.root) {

    open fun bind(quote: Quote) {
        layoutBinding.binding = QuoteBinding(adapterDependency, quote)
        layoutBinding.executePendingBindings()
    }
}