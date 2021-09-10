package com.sarftec.lifequotesandcaptions.presentation.image

/*
class FirebaseImages(context: Context)
    : ImagesRepo(context, context.filesDir.toString()) {

    private val preferenceMap = hashMapOf<String, Preferences.Key<Boolean>>()

   private suspend fun load(
        folder: String,
        preference: Preferences.Key<Boolean>
    ): Result<Unit> {
        var result = Result.failure<Unit>(Exception("Image load failed!!"))

        if (!context.readSettings(preference, false).first()) {
            result = downloadImages(folder).also {
                if (result.isSuccess) context.editSettings(preference, true)
            }
        }
        if (result.isSuccess) storeImageNames(folder)
        return result
    }

    private suspend fun downloadImages(folder: String): Result<Unit> {
        val deferredResult = CompletableDeferred<Result<Unit>>()
        val listRef = Firebase.storage.reference.child(folder)
        try {
            val files = mutableListOf<FileDownloadTask>()
            listRef.listAll()
                .await()
                .items
                .forEach {
                    val imageFolder = File(context.filesDir, folder)
                    imageFolder.mkdir()
                    try {
                        val file = it.getFile(
                            File(
                                imageFolder,
                                it.name.substringAfter("/").lowercase(Locale.ENGLISH)
                            )
                        )
                        files.add(file)
                    } catch (e: Exception) {
                        deferredResult.complete(Result.failure(e))
                    }
                }
            files.forEach { it.await() }
            deferredResult.complete(Result.success(Unit))
        } catch (e: Exception) {
            deferredResult.complete(Result.success(Unit))
        }
        deferredResult.complete(Result.success(Unit))
        return deferredResult.await()
    }

    override suspend fun load(folder: String): Result<Unit> {
        preferenceMap.getOrPut(folder) {
            booleanPreferencesKey(folder)
        }
        return load(folder, preferenceMap[folder]!!)
    }

    override fun loadImagesNames(folder: String): List<String> {
        return File(context.filesDir, folder).list()!!.toList()
    }

    override fun getImageHolder(uri: Uri): ImageHolder {
        return ImageHolder.FileImage(uri)
    }
}
 */