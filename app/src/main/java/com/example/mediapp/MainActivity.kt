package com.example.mediapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mediapp.ui.screens.HomeScreen
import com.example.mediapp.ui.screens.LoginScreen
import com.example.mediapp.ui.screens.PedirConsultaScreen
import com.example.mediapp.ui.screens.VerConsultas
import com.example.mediapp.ui.screens.VerRecetas
import com.example.mediapp.ui.theme.MediAppTheme
import com.example.mediapp.viewmodel.TokenViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MediAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavHost()
                }
            }
        }
    }
}

@Composable
fun AppNavHost(viewModel: TokenViewModel = viewModel()) {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                viewModel = viewModel,
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                navigatePedirConsulta = {
                    navController.navigate("pedir_consulta") {
                        popUpTo("home") { inclusive = false }
                    }
                },
                navigateVerRecetas = {
                    navController.navigate("ver_recetas") {
                        popUpTo("home") { inclusive = false }
                    }
                },
            ) {
                navController.navigate("ver_consultas") {
                    popUpTo("home") { inclusive = false }
                }
            }

        }
        composable("pedir_consulta") {
            PedirConsultaScreen(viewModel = viewModel, navController = navController)
        }
        composable("ver_recetas") {
            VerRecetas(viewModel = viewModel, navController = navController)
        }
        composable("ver_consultas") {
            VerConsultas(viewModel = viewModel, navController = navController)
        }
    }
}