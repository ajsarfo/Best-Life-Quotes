package com.sarftec.lifequotesandcaptions.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarftec.lifequotesandcaptions.model.Category
import com.sarftec.lifequotesandcaptions.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>>
    get() = _categories

    fun fetch() {
        viewModelScope.launch {
            _categories.value = repository.getCategories()
        }
    }
}