package com.sarftec.lifequotesandcaptions.presentation.image

import android.net.Uri

sealed class ImageHolder(val uri: Uri) {

    class AssetImage(
        uri: Uri,
        val allowPlaceHolder: Boolean = false
    ) : ImageHolder(uri)

    class FileImage(
        uri: Uri,
        val allowPlaceHolder: Boolean = false
    ) : ImageHolder(uri)

    object Empty : ImageHolder(Uri.parse("file://"))
}