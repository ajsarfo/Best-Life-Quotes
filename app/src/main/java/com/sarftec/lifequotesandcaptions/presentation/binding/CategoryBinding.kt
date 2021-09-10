package com.sarftec.lifequotesandcaptions.presentation.binding

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.sarftec.lifequotesandcaptions.BR
import com.sarftec.lifequotesandcaptions.model.Category
import com.sarftec.lifequotesandcaptions.presentation.adapter.CategoryAdapter
import com.sarftec.lifequotesandcaptions.presentation.image.ImageHolder
import com.sarftec.lifequotesandcaptions.presentation.utils.bindable

class CategoryBinding(
    private val adapterDependency: CategoryAdapter.AdapterDependency,
    val category: Category,
) : BaseObservable() {

    @get:Bindable
    val holder:ImageHolder by bindable(
        adapterDependency.dependency.imageStore.getCategoryImage(category.name),
        BR.holder
    )

    fun clicked() {
        adapterDependency.onClick(category)
    }
}