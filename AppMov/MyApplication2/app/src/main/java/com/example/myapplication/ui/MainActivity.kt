package com.example.myapplication.ui

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.GourmetManagerApp
import com.example.myapplication.ui.theme.GourmetManagerTheme
import com.example.myapplication.ui.viewmodels.AppViewModel
import com.example.myapplication.ui.viewmodels.HomeViewModel
import com.example.myapplication.ui.viewmodels.OrderViewModel
import com.example.myapplication.ui.viewmodels.MenuViewModel
import com.example.myapplication.ui.viewmodels.ReservationViewModel


class MainActivity : ComponentActivity() {
    private lateinit var viewModel: AppViewModel
    private lateinit var menuViewModel: MenuViewModel
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var reservationViewModel: ReservationViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[AppViewModel::class.java]
        orderViewModel = ViewModelProvider(this)[OrderViewModel::class.java]
        menuViewModel = ViewModelProvider(this)[MenuViewModel::class.java]
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        reservationViewModel = ViewModelProvider(this)[ReservationViewModel::class.java]

        setContent {

            val darkTheme by viewModel.darkTheme.collectAsState()

            GourmetManagerTheme (darkTheme = darkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    GourmetManagerApp(
                        viewModel,
                        orderViewModel,
                        menuViewModel,
                        homeViewModel,
                        reservationViewModel)
                }
            }
        }
    }
}

