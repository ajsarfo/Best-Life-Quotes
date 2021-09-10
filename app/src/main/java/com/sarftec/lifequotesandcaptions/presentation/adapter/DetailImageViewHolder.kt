package com.sarftec.lifequotesandcaptions.presentation.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifequotesandcaptions.presentation.Dependency
import com.sarftec.lifequotesandcaptions.presentation.image.ImageHolder
import com.sarftec.lifequotesandcaptions.presentation.utils.glideLoad

class DetailImageViewHolder(
    private val dependency: Dependency,
    private val imageView: AppCompatImageView,
    private val onClick: (ImageHolder) -> Unit
) : RecyclerView.ViewHolder(imageView) {

    fun bind(uri: ImageHolder) {
        imageView.glideLoad(uri)
        imageView.setOnClickListener {
            onClick(uri)
        }
    }
}