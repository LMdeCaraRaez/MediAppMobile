package com.example.mediapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.mediapp.viewmodel.TokenViewModel

@Composable
fun LoginScreen(
    viewModel: TokenViewModel = viewModel(),
    onLoginSuccess: () -> Unit
) {
    val token = viewModel.token
    val user = viewModel.user
    val error = viewModel.error
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),

        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Iniciar sesión", color = Color.Black)

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Usuario") },
                    singleLine = true
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Contraseña") },
                    visualTransformation = PasswordVisualTransformation(),
                    singleLine = true
                )


                Button(
                    onClick = { viewModel.login(username, password) },
                    modifier = Modifier.align(Alignment.CenterHorizontally), colors = ButtonDefaults.buttonColors(
                        containerColor =  Color(0xFF1565C0)
                    )
                ) {
                    Text("Iniciar sesión")
                }

                if (token != null) {

                    if (user != null) {
                        Text(text = user.dni)
                        Text(text = user.nombre)
                        Text(text = user.apellidos)
                    }
                }

                LaunchedEffect(error) {
                    error?.let {
                        Toast.makeText(context, "Usuario o contraseña incorrectos, inténtelo de nuevo", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    LaunchedEffect(token) {
        if (token != null) {
            onLoginSuccess()
        }
    }
}