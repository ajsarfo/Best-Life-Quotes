package com.sarftec.lifequotesandcaptions.model

import androidx.room.Entity
import androidx.room.PrimaryKey

const val CATEGORY_TABLE = "category_table"

@Entity(tableName = CATEGORY_TABLE)
class Category(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val name: String
)