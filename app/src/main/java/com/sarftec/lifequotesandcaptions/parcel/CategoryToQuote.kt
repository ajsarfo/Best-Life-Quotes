package com.sarftec.lifequotesandcaptions.parcel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class CategoryToQuote(
    val categoryId: Int,
    val categoryName: String
) : Parcelable