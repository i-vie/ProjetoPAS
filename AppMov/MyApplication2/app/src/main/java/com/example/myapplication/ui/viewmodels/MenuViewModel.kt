package com.example.myapplication.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repositories.CategoryRepository
import com.example.myapplication.data.repositories.ProductRepository
import com.example.myapplication.ui.models.Category
import com.example.myapplication.ui.models.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MenuViewModel : ViewModel() {

    private val productRepository = ProductRepository()
    private val categoryRepository = CategoryRepository()

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }

    private val _productList = MutableStateFlow<List<Product>>(emptyList())
    val productList: StateFlow<List<Product>> = _productList


    fun addProduct(product: Product) {
        viewModelScope.launch {
            productRepository.createProduct(product)
            _productList.value = productRepository.getActiveProducts()
        }
    }

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            try {
                Log.d("MenuScreen", "updatedProduct: $product")
                productRepository.updateProduct(product)
            } catch (e: Exception) {
                Log.d("MenuScreen", "Erro ao atualizar produto: ${e.message}")
            }
        }
    }

    fun getProductsFromCategory(category: Category) {
        viewModelScope.launch {
            val products = productRepository.getProductsFromCategory(category.id)
            _productList.value = products
        }
    }

    fun deactivateProduct(product: Product) {
        val updatedProduct = product.copy(active = 0)
        viewModelScope.launch {
            productRepository.updateProduct(updatedProduct)
            val products = productRepository.getProductsFromCategory(product.categoryId)
            _productList.value = products
        }
    }

    private val _categoryList = MutableStateFlow<List<Category>>(emptyList())
    val categoryList: StateFlow<List<Category>> = _categoryList //list of all categories
    private val _categoryResult = MutableStateFlow<Category?>(null)
    val categoryResult: MutableStateFlow<Category?> = _categoryResult

    fun getCategoryById(selectedCategoryID: Int) {
        viewModelScope.launch {
            val category = categoryRepository.getCategoryById(selectedCategoryID)
            _categoryResult.value = category
        }
    }

    init {
        viewModelScope.launch {
            val categories = categoryRepository.getCategories()
            _categoryList.value = categories
        }
    }


    fun getCategories() {
        viewModelScope.launch {
            val categories = categoryRepository.getCategories()
            _categoryList.value = categories
        }
    }


    private val _selectedCategory = MutableStateFlow<Category>(Category(0,""))
    var selectedCategory: StateFlow<Category> = _selectedCategory



    //var selectedCategory by mutableStateOf(Category(0,""))

    fun selectCategory(category: Category) {
        _selectedCategory.value = category
    }


    //add new category
    fun addCategory(name: String) {
        viewModelScope.launch {
            categoryRepository.insert(Category(name = name))
            _categoryList.value = categoryRepository.getCategories()
        }
    }

    //update existing category
    fun updateCategory(category: Category) {
        viewModelScope.launch {
            //categoryDao.update(category)
            //_categoryList.value = categoryDao.getAllCategories()
        }
    }

    fun deactivateCategory(category: Category) {
        val updatedCategory = category.copy(active = 0)
        viewModelScope.launch {
            val productsFromCategory = productRepository.getProductsFromCategory(category.id)
            for (product in productsFromCategory) {
                val updatedProduct = product.copy(active = 0)
                productRepository.updateProduct(updatedProduct)
            }
            categoryRepository.updateCategory(updatedCategory)
            _categoryList.value = categoryRepository.getCategories()
        }
    }

}