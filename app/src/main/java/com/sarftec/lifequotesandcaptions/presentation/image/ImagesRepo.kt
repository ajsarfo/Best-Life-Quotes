package com.sarftec.lifequotesandcaptions.presentation.image

import android.content.Context
import android.net.Uri
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

abstract class ImagesRepo(
    protected val context: Context,
    private val parentPath: String
) {

    private val imagesMap = HashMap<String, HashSet<String>>()

    fun reloadImages(vararg folders: String) {
        folders.forEach {
            imagesMap[it] ?: storeImageNames(it)
        }
    }

    fun getImages(folder: String): Result<List<ImageHolder>> {
        return imagesMap[folder]?.let { images ->
            Result.success(
                images.map {
                    getImageHolder(it.toStorageUri(folder))
                }
            )
        } ?: Result.failure(Exception("Error: Cannot find images in $folder"))
    }

    fun getImage(name: String, folder: String): Result<ImageHolder> {
        val converted = name.replace(" ", "_").lowercase(Locale.ENGLISH)
        return imagesMap[folder]
            ?.firstOrNull { it.startsWith(converted) }
            ?.toStorageUri(folder)
            ?.let { Result.success(getImageHolder(it)) }
            ?: Result.failure(Exception("Error: Cannot find image $name in $folder"))
    }

    abstract suspend fun load(folder: String): Result<Unit>

    protected abstract fun loadImagesNames(folder: String) : List<String>

    protected abstract fun getImageHolder(uri: Uri) : ImageHolder

    protected fun storeImageNames(folder: String) {
        imagesMap[folder] = loadImagesNames(folder)
            .map { it.lowercase(Locale.ENGLISH) }
            .toHashSet()
      /*
        imagesMap[folder] = File(storageDir, folder)
            .also { Log.v("TAG", "${it.path}") }
            .list()!!
            .map { it.lowercase(Locale.ENGLISH) }
            .toHashSet()
       */
    }

    private fun String.toStorageUri(folder: String): Uri {
        return Uri.parse("${parentPath}/$folder/$this")
    }
}