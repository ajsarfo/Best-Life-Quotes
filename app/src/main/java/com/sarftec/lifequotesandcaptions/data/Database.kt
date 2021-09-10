package com.sarftec.lifequotesandcaptions.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sarftec.lifequotesandcaptions.data.dao.CategoryDao
import com.sarftec.lifequotesandcaptions.data.dao.QuoteDao
import com.sarftec.lifequotesandcaptions.model.Category
import com.sarftec.lifequotesandcaptions.model.Quote

@Database(entities = [Category::class, Quote::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun quoteDao() : QuoteDao
    abstract fun categoryDao() : CategoryDao
}