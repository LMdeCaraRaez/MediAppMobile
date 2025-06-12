package com.example.mediapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mediapp.viewmodel.TokenViewModel

@Composable
fun VerRecetas(viewModel: TokenViewModel, navController: NavHostController) {
    LaunchedEffect(Unit) {
        viewModel.getRecetas()
    }
    
    Text(text = "Ver recetas", Modifier.background(Color.Black))

    when {
        viewModel.recetasResponse != null -> {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(viewModel.recetasResponse!!.resultados.size) { index ->
                    val receta = viewModel.recetasResponse!!.resultados[index]
                    Text(text = receta.precio.toString())
                }
            }
        }

        viewModel.error != null -> {
            Text("Error: ${viewModel.error}")
        }

        else -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
                CircularProgressIndicator()
            }
        }
    }
}