package com.sarftec.lifequotesandcaptions.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.sarftec.lifequotesandcaptions.editSettings
import com.sarftec.lifequotesandcaptions.model.Category
import com.sarftec.lifequotesandcaptions.model.Quote
import com.sarftec.lifequotesandcaptions.readSettings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import java.util.*
import javax.inject.Inject

class DatabaseSetup @Inject constructor(
    @ApplicationContext private val context: Context,
    private val database: Database
) {

    private val quoteFiles by lazy {
        context.assets.list(QUOTE_FOLDER)!!
    }

    suspend fun isPrepared(): Boolean {
        return context.readSettings(isSetup, false).first()
    }


    private fun getCategory(fileName: String): String {
        return fileName
            .substringBeforeLast("_")
            .replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.ENGLISH) else it.toString()
            }
    }

    private fun getIndex(fileName: String): Int {
        return fileName.substringAfterLast("_").toInt()
    }

    suspend fun prepare() {
        database.categoryDao().insert(
            quoteFiles.map {
                Category(
                    id = getIndex(it),
                    name = getCategory(it)
                )
            }
        )
        quoteFiles.forEach { fileName ->
            val quotes = context.assets
                .open("$QUOTE_FOLDER/$fileName")
                .bufferedReader()
                .useLines { sequence ->
                sequence.map { line ->
                    Quote(
                        categoryId = getIndex(fileName),
                        quote = line
                    )
                }.toList()
            }
            database.quoteDao().insert(quotes)
        }
        context.editSettings(isSetup, true)
    }


    companion object {
        const val QUOTE_FOLDER = "quote_files"
        val isSetup = booleanPreferencesKey("app_created")
    }
}