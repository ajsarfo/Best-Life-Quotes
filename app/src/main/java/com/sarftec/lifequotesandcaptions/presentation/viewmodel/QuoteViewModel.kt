package com.sarftec.lifequotesandcaptions.presentation.viewmodel

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sarftec.lifequotesandcaptions.model.Quote
import com.sarftec.lifequotesandcaptions.parcel.CategoryToQuote
import com.sarftec.lifequotesandcaptions.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class QuoteViewModel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle
) : BaseQuoteViewModel() {


    override fun fetch() {
        if(_quotes.value != null) return
        val parcel = savedStateHandle.get<Parcelable>(PARCEL) as CategoryToQuote? ?: return
        viewModelScope.launch {
            _quotes.value = repository.getQuotes(parcel.categoryId).let {
                val seed = (0 until 100).random()
                val randGenerator = Random(seed)
                savedStateHandle.set(SEED, seed)
                it.shuffled(randGenerator)
            }.toMutableList()
        }
    }

    override fun getToolbarTitle(): String? {
        val parcel = savedStateHandle.get<Parcelable>(PARCEL) as CategoryToQuote?
        return parcel?.let {
            "${it.categoryName} Quotes"
        }
    }

    override fun saveQuote(quote: Quote) {
        viewModelScope.launch {
            repository.saveFavoriteQuote(quote.id, quote.isFavorite)
        }
    }

    fun setParcel(parcel: CategoryToQuote) {
        if (savedStateHandle.get<Parcelable>(PARCEL) != null) return
        savedStateHandle.set(PARCEL, parcel)
    }

    fun getSeed() : Int {
        return savedStateHandle.get<Int>(SEED) ?: 0
    }

    companion object {
        const val PARCEL = "parcel"
        const val SEED = "seed"
    }
}