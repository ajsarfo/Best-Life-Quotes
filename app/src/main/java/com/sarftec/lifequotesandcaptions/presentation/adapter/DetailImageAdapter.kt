package com.sarftec.lifequotesandcaptions.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifequotesandcaptions.databinding.LayoutDetailImageBinding
import com.sarftec.lifequotesandcaptions.presentation.Dependency
import com.sarftec.lifequotesandcaptions.presentation.image.ImageHolder

class DetailImageAdapter(
    private val dependency: Dependency,
    private val onClick: (ImageHolder) -> Unit
) : RecyclerView.Adapter<DetailImageViewHolder>() {

    private val items by lazy {
        dependency.imageStore.getQuoteImages()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailImageViewHolder {
        val layoutBinding = LayoutDetailImageBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DetailImageViewHolder(dependency, layoutBinding.root, onClick)
    }

    override fun onBindViewHolder(holder: DetailImageViewHolder, position: Int) {
     holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}