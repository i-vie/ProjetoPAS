package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.repositories.CategoryRepository
import com.example.myapplication.data.repositories.ProductRepository
import com.example.myapplication.ui.models.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoryViewModel : ViewModel() {

    //private val categoryDao = AppDatabase.getDatabase(application).categoryDao()
    private val categoryRepository = CategoryRepository()
    private val productRepository = ProductRepository()
    private val _categoryList = MutableStateFlow<List<Category>>(emptyList())
    val categoryList: StateFlow<List<Category>> = _categoryList //list of all categories


}