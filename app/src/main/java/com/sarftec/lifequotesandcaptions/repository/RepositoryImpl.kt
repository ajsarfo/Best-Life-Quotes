package com.sarftec.lifequotesandcaptions.repository

import com.sarftec.lifequotesandcaptions.data.Database
import com.sarftec.lifequotesandcaptions.model.Category
import com.sarftec.lifequotesandcaptions.model.Quote
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val database: Database
): Repository {

    private var todayQuote: Quote? = null

    override suspend fun getCategories(): List<Category> {
       return database.categoryDao().categories()
    }

    override suspend fun getQuotes(categoryId: Int): List<Quote> {
        return database.quoteDao().quotes(categoryId)
    }

    override suspend fun todayQuote(): Quote {
        return todayQuote ?: database.quoteDao().randomQuote().also {
            todayQuote = it
        }
    }

    override suspend fun getFavoriteQuotes(): List<Quote> {
        return database.quoteDao().favorites()
    }

    override suspend fun saveFavoriteQuote(quoteId: Int, isFavorite: Boolean) {
       return database.quoteDao().updateFavorite(quoteId, isFavorite)
    }
}