package com.sarftec.lifequotesandcaptions.parcel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuoteToDetail(
   val quoteId: Int = -1,
   val categoryId: Int = - 1,
   val categoryTitle: String = "",
   val seed: Int = -1,
   val origin: Int = -1,
   val quote: String =""
) : Parcelable {

   companion object {
      const val ORIGIN_QUOTE = 0
      const val ORIGIN_FAVORITE = 1
      const val ORIGIN_MAIN = 3
   }
}