package com.sarftec.lifequotesandcaptions.presentation.adapter

import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifequotesandcaptions.databinding.LayoutCategoryBinding
import com.sarftec.lifequotesandcaptions.model.Category
import com.sarftec.lifequotesandcaptions.presentation.binding.CategoryBinding

class CategoryViewHolder(
    private val layoutBinding : LayoutCategoryBinding,
    private val adapterDependency: CategoryAdapter.AdapterDependency
) : RecyclerView.ViewHolder(layoutBinding.root) {

    fun bind(model : Category) {
        layoutBinding.binding = CategoryBinding(adapterDependency,model)
        layoutBinding.executePendingBindings()
    }
}