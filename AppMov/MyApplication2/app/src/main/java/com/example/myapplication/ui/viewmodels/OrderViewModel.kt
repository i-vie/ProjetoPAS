package com.example.myapplication.ui.viewmodels

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repositories.CategoryRepository
import com.example.myapplication.data.repositories.OrderRepository
import com.example.myapplication.data.repositories.ProductRepository
import com.example.myapplication.data.repositories.TableRepository
import com.example.myapplication.ui.models.Category
import com.example.myapplication.ui.models.Order
import com.example.myapplication.ui.models.OrderItem
import com.example.myapplication.ui.models.Product
import com.example.myapplication.ui.models.Table
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OrderViewModel : ViewModel() {

    private val orderRepository = OrderRepository()
    private val productRepository = ProductRepository()
    private val categoryRepository = CategoryRepository()


    //selected order (to view details or edit)
    private val _selectedOrder = MutableStateFlow<Order>(Order(0, 0, 0, 0, 0.0))
    val selectedOrder: StateFlow<Order> = _selectedOrder

    fun selectOrder(order: Order) {
        _selectedOrder.value = order
    }

    //lists of orders (open, by current employee, total orders of current day)
    private val _openOrders = MutableStateFlow<List<Order>>(emptyList())
    val openOrders: StateFlow<List<Order>> = _openOrders //list open orders

    //open orders
    fun getOpenOrders() {
        viewModelScope.launch {
            val orders = orderRepository.getOrdersByStatus(isOpen = 1)
            _openOrders.value = orders
        }
    }

    private val _myOrders = MutableStateFlow<List<Order>>(emptyList())
    val myOrders: MutableStateFlow<List<Order>> = _myOrders

    //pedidos do funcionário com sessão iniciada
    fun getMyOrders(employeeId: Int) {
        viewModelScope.launch {
            val orders = orderRepository.getOrdersByEmployeeId(employeeId)
            _myOrders.value = orders
        }
    }

    private val _orderList = MutableStateFlow<List<Order>>(emptyList())
    val orderList: StateFlow<List<Order>> = _orderList

    //all orders of current day
    fun getOrdersToday() {
        viewModelScope.launch {
            val orders = orderRepository.getOrdersToday()
            _orderList.value = orders
        }
    }

    //number of last order created
    private val _orderNr = MutableStateFlow<Int>(0)
    val orderNr: StateFlow<Int> = _orderNr

    fun addOrderWithItems(order: Order, orderItems: List<OrderItem>) {
        viewModelScope.launch {

            val orderNr = orderRepository.createOrder(order)!!
            orderItems.forEach {
                it.orderId = orderNr
            }
            for (item in orderItems) {
                orderRepository.insertOrderItem(item)
            }
            _orderNr.value = orderNr
            getOrdersToday()
        }
    }


    //updates the order items for an order
    fun updateOrderItems(orderNr: Int, orderItems: List<OrderItem>) {
        viewModelScope.launch {
            val currentOrderItems = orderRepository.getOrderItemsByOrderId(orderNr)
            for (item in currentOrderItems) {
                orderRepository.deleteOrderItem(item)
            }
            orderItems.forEach { it.orderId = orderNr }
            for (item in orderItems) {
                if (item.quantity > 0) {
                    orderRepository.insertOrderItem(item)
                }
            }
        }
    }

    private val _orderItems = MutableStateFlow<List<OrderItem>>(emptyList())
    val orderItems: StateFlow<List<OrderItem>> = _orderItems

    //list of products and respective quantities by order id
    fun getOrderItemsByOrderId(orderNr: Int) = viewModelScope.launch {
        viewModelScope.launch {
            val orderItems = orderRepository.getOrderItemsByOrderId(orderNr)
            _orderItems.value = orderItems
        }

    }

    fun deleteOrder(order: Order) {
        viewModelScope.launch {
            orderRepository.deleteOrder(order)
        }
    }

    fun closeOrder(order: Order) {
        val updatedOrder = order.copy(open = 0)
        viewModelScope.launch {
            orderRepository.updateOrder(updatedOrder)
        }
    }

    fun updateOrder(order: Order, newTable: Int, newTotal: Double) {
        val updatedOrder = order.copy(tableNr = newTable, total = newTotal)
        viewModelScope.launch {
            orderRepository.updateOrder(updatedOrder)
        }
    }


    private val _productResult = MutableStateFlow<Product?>(null)
    val productResult: StateFlow<Product?> = _productResult

    fun getProductById(productId: Int): StateFlow<Product?> {
        val product = MutableStateFlow<Product?>(null)
        viewModelScope.launch {
            val product = productRepository.getProductById(productId)!!
            _productResult.value = product
        }
        return product
    }

    private val _selectedCategory = MutableStateFlow<Category?>(null)
    val selectedCategory: StateFlow<Category?> = _selectedCategory

    fun selectCategory(category: Category) {
        _selectedCategory.value = category
    }

    private val _productList = MutableStateFlow<List<Product>>(emptyList())
    val productList: StateFlow<List<Product>> = _productList

    fun getProductsFromCategory(category: Category) {
        viewModelScope.launch {
            val products = productRepository.getProductsFromCategory(category.id)
            _productList.value = products
        }
    }

    private val _categoryList = MutableStateFlow<List<Category>>(emptyList())
    val categoryList: StateFlow<List<Category>> = _categoryList //list of all categories

    fun getCategories() {
        viewModelScope.launch {
            val categories = categoryRepository.getCategories()
            _categoryList.value = categories
        }
    }

    //Tables
    private val tableRepository = TableRepository()
    private val _tableList = MutableStateFlow<List<Table>>(emptyList())
    val tableList: StateFlow<List<Table>> = _tableList //list of tables categories

    fun getTables() {
        viewModelScope.launch {
            val tables = tableRepository.getTables()
            _tableList.value = tables
        }
    }

    var listProducts = mutableStateListOf<Pair<Product, Int>>()
    var selectedTable = mutableIntStateOf(1)
    var new = mutableStateOf(true)

    fun clearOrder() {
        listProducts.clear()
        selectedTable.intValue = 1
    }

}