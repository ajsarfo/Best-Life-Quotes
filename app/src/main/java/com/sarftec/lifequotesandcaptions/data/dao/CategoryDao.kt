package com.sarftec.lifequotesandcaptions.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarftec.lifequotesandcaptions.model.CATEGORY_TABLE
import com.sarftec.lifequotesandcaptions.model.Category

@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(categories: List<Category>)

    @Query("select * from $CATEGORY_TABLE")
    suspend fun categories() : List<Category>
}