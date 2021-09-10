package com.sarftec.lifequotesandcaptions.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifequotesandcaptions.databinding.LayoutQuoteBinding
import com.sarftec.lifequotesandcaptions.model.Quote
import com.sarftec.lifequotesandcaptions.presentation.Dependency
import com.sarftec.lifequotesandcaptions.presentation.viewmodel.BaseQuoteViewModel

open class QuoteAdapter(
    private val dependency: Dependency,
    private val viewModel: BaseQuoteViewModel,
    private val onClick: (Quote) -> Unit
) : RecyclerView.Adapter<QuoteViewHolder>() {

    protected var items: List<Quote> = emptyList()

    protected val adapterDependency = AdapterDependency(dependency, viewModel, onClick)

    inner class AdapterDependency(
        val dependency: Dependency,
        val viewModel: BaseQuoteViewModel,
        val onClick: (Quote) -> Unit
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
       val layoutBinding = LayoutQuoteBinding.inflate(
           LayoutInflater.from(parent.context),
           parent,
           false
       )
        return QuoteViewHolder(layoutBinding, adapterDependency)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
      holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    open fun resetQuoteFavorites(indexedFavorites: Set<Map.Entry<Int, Boolean>>) {
        indexedFavorites.forEach {
            items[it.key].isFavorite = it.value
            notifyItemChanged(it.key)
        }
    }

    fun submitData(items: List<Quote>) {
        this.items = items;
        notifyDataSetChanged()
    }
}