package com.sarftec.lifequotesandcaptions.presentation.utils

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BaseObservable
import androidx.databinding.BindingAdapter
import com.sarftec.lifequotesandcaptions.presentation.image.ImageHolder
import kotlin.reflect.KProperty

class Bindable<T : Any>(private var value: T, private val tag: Int) {
    operator fun <U : BaseObservable> getValue(ref: U, property: KProperty<*>): T = value
    operator fun <U : BaseObservable> setValue(ref: U, property: KProperty<*>, newValue: T) {
        value = newValue
        ref.notifyPropertyChanged(tag)
    }
}

fun <T : Any> bindable(value: T, tag: Int): Bindable<T> = Bindable(value, tag)

@BindingAdapter("holder")
fun setImage(imageView: ImageView, imageHolder: ImageHolder) {
    imageView.glideLoad(imageHolder)
}

@BindingAdapter("drawable")
fun setDrawable(imageView: ImageView, @DrawableRes id: Int) {
   imageView.setImageResource(id)
}