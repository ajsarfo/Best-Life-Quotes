package com.sarftec.lifequotesandcaptions.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sarftec.lifequotesandcaptions.databinding.LayoutCategoryBinding
import com.sarftec.lifequotesandcaptions.model.Category
import com.sarftec.lifequotesandcaptions.presentation.Dependency

class CategoryAdapter(
    private val dependency: Dependency,
    private val onClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryViewHolder>() {

    private var list = emptyList<Category>()

    private val adapterDependency = AdapterDependency(
        dependency,
        onClick
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutBinding = LayoutCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(layoutBinding, adapterDependency)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun submitData(list: List<Category>) {
        this.list = list
        notifyDataSetChanged()
    }

    inner class AdapterDependency(
        val dependency: Dependency,
        val onClick: (Category) -> Unit
    )
}