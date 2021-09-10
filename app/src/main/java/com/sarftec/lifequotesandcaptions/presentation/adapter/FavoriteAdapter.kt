package com.sarftec.lifequotesandcaptions.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.sarftec.lifequotesandcaptions.databinding.LayoutQuoteBinding
import com.sarftec.lifequotesandcaptions.model.Quote
import com.sarftec.lifequotesandcaptions.presentation.Dependency
import com.sarftec.lifequotesandcaptions.presentation.viewmodel.BaseQuoteViewModel

class FavoriteAdapter(
    dependency: Dependency,
    viewModel: BaseQuoteViewModel,
    onClick: (Quote) -> Unit
) : QuoteAdapter(dependency, viewModel, onClick) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val layoutBinding = LayoutQuoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return FavoriteViewHolder(layoutBinding, adapterDependency) { quote ->
            val index = items.indexOfFirst { quote.id == it.id }
            if(index != -1) {
                notifyItemRemoved(index)
            }
        }
    }

    override fun resetQuoteFavorites(indexedFavorites: Set<Map.Entry<Int, Boolean>>) {

    }
}