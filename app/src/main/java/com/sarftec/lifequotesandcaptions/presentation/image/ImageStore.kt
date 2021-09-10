package com.sarftec.lifequotesandcaptions.presentation.image

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val assetImages: ImagesRepo = AssetImages(context)

    private val imageFolders = listOf(
        CATEGORY_FOLDER,
        QUOTES_FOLDER,
        BACKGROUND_FOLDER
    )

    suspend fun loadImages() {
        imageFolders.forEach { folder ->
            assetImages.load(folder).onFailure { throw it }
        }
    }

    fun reload() {
      assetImages.reloadImages(*imageFolders.toTypedArray())
    }

    fun getCategoryImage(category: String): ImageHolder {
        return getResult(assetImages.getImage(category, CATEGORY_FOLDER)) {
          ImageHolder.Empty
        }
    }

    fun getQuoteActivityBackground(): ImageHolder {
        return getResult(assetImages.getImages(BACKGROUND_FOLDER)) {
            listOf(ImageHolder.Empty)
        }.random()
    }

    fun getRandomQuoteImage(): ImageHolder {
        return getResult(assetImages.getImages(QUOTES_FOLDER)) {
            listOf(ImageHolder.Empty)
        }.random()
    }

    fun getQuoteImages(): List<ImageHolder> {
        return getResult(assetImages.getImages(QUOTES_FOLDER)) {
            listOf(ImageHolder.Empty)
        }
    }

    private fun <T> getResult(result: Result<T>, onFailure: (Exception) -> T) : T {
        var value: T? = null
        result.onSuccess { value = it }
        result.onFailure { value = onFailure(Exception(it.message)) }
        return value!!
    }

    companion object {
        const val CATEGORY_FOLDER = "category_images"
        const val QUOTES_FOLDER = "quote_images"
        const val BACKGROUND_FOLDER = "background_images"
    }
}