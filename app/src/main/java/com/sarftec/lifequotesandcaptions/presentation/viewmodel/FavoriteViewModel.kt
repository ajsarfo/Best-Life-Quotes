package com.sarftec.lifequotesandcaptions.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.sarftec.lifequotesandcaptions.model.Quote
import com.sarftec.lifequotesandcaptions.presentation.activity.BaseActivity
import com.sarftec.lifequotesandcaptions.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: Repository
) : BaseQuoteViewModel() {

    override fun fetch() {
        if (_quotes.value != null) return
        viewModelScope.launch {
            _quotes.value = repository.getFavoriteQuotes().toMutableList()
        }
    }

    override fun getToolbarTitle(): String? = "Favorite Quotes"

    override fun saveQuote(quote: Quote) {
        viewModelScope.launch {
            repository.saveFavoriteQuote(quote.id, quote.isFavorite)
        }
    }

    fun resetQuoteFavorites() {
        viewModelScope.launch {
            val pairs = BaseActivity.modifiedQuoteList.entries
            _quotes.value?.let { quotes ->
                pairs.forEach {
                    repository.saveFavoriteQuote(quotes[it.key].id, it.value)
                }
            }
            if (pairs.isNotEmpty()) _quotes.value = repository.getFavoriteQuotes().toMutableList()
            BaseActivity.modifiedQuoteList.clear()
        }
    }

    override fun saveAndRemove(quote: Quote) {
        viewModelScope.launch {
            _quotes.value?.remove(quote)
            quote.isFavorite = !quote.isFavorite
            repository.saveFavoriteQuote(quote.id, quote.isFavorite)
        }
    }
}