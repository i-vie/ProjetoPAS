package com.example.myapplication.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.AppDatabase
import com.example.myapplication.data.entity.EmployeeEntity
import com.example.myapplication.data.repositories.EmployeeRepository
import com.example.myapplication.ui.models.Employee
import com.example.myapplication.ui.models.LoginRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class AppViewModel(application: Application) :
    AndroidViewModel(application) {

        //set the theme light or dark
    private val _darkTheme = MutableStateFlow<Boolean>(false)
    val darkTheme: StateFlow<Boolean> = _darkTheme

    fun setTheme(darkTheme: Boolean) {
        viewModelScope.launch {
            _darkTheme.value = darkTheme
        }
    }

    private val _isLoading = MutableStateFlow<Boolean>(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun setLoading(isLoading: Boolean) {
        viewModelScope.launch {
            _isLoading.value = isLoading
        }
    }

    private val employeeDao = AppDatabase.getDatabase(application).employeeDao()

    //funcionário com sessão iniciada

    private val employeeRepository = EmployeeRepository()

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: MutableLiveData<String> get() = _errorMessage

    private val _loginResult = MutableStateFlow<Employee?>(null)
    val loginResult: StateFlow<Employee?> = _loginResult

    private val _loggedInEmployee = MutableStateFlow<Employee?>(null)
    val loggedInEmployee: StateFlow<Employee?> = _loggedInEmployee

    fun setLoggedInEmployee(employee: Employee) {
        _loggedInEmployee.value = employee
        _isUserLoggedIn.value = true
    }

    //gets EmployeeEntity from db and creates a Employee object with the retrieved data
    fun getLoggedInEmployee() {
        viewModelScope.launch {
            val employee = employeeDao.getEmployee()
            if (employee != null) {
                _loggedInEmployee.value = Employee(
                    employee.id,
                    employee.name,
                    employee.username,
                    employee.email,
                    employee.admin
                )
            }
        }
    }

    fun login(username: String?, email: String?, password: String) {
        val loginRequest = LoginRequest(username, email, password)

        viewModelScope.launch {
            try {
                val loggedInEmployee = employeeRepository.login(loginRequest)
                Log.d("AppViewModel", "$loggedInEmployee")
                loggedInEmployee?.let {
                    EmployeeEntity(
                        it.id,
                        it.name,
                        it.username,
                        it.email,
                        it.admin
                    )
                }?.let { employeeDao.insert(it) }
                _loginResult.value = loggedInEmployee
                Log.d("AppViewModel result", "${_loginResult.value}")
            } catch (e: Exception) {
                _errorMessage.postValue("Erro: ${e.message}")
            }
        }
    }

    private val _isUserLoggedIn = MutableStateFlow<Boolean>(false)
    val isUserLoggedIn: StateFlow<Boolean> = _isUserLoggedIn

    fun isUserLoggedIn() {
        viewModelScope.launch {
            val employee = employeeDao.getEmployee()
            _isUserLoggedIn.value = (employee != null)
        }
    }

    //when user logs out, employee data is deleted from RoomDB
    fun logout() {
        viewModelScope.launch {
            val employee = employeeDao.getEmployee()
            employee?.let {
                employeeDao.delete(it)
            }
        }
    }

}


