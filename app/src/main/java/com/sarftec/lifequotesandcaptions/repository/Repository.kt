package com.sarftec.lifequotesandcaptions.repository

import com.sarftec.lifequotesandcaptions.model.Category
import com.sarftec.lifequotesandcaptions.model.Quote

interface Repository {
    suspend fun getCategories() : List<Category>
    suspend fun getQuotes(categoryId: Int) : List<Quote>
    suspend fun todayQuote() : Quote
    suspend fun getFavoriteQuotes() : List<Quote>
    suspend fun saveFavoriteQuote(quoteId: Int, isFavorite: Boolean)
}