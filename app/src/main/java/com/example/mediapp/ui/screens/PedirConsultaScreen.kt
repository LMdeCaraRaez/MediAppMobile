package com.example.mediapp.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mediapp.ui.theme.Secundario
import com.example.mediapp.viewmodel.TokenViewModel


@Composable
fun PedirConsultaScreen(viewModel: TokenViewModel, navController: NavHostController) {
    val fecha = remember { mutableStateOf("") }
    val hora = remember { mutableStateOf("") }
    val paciente = "pacopizzaDoctorAlcala"
    val medico = "pacopizzaDoctorAlcala"
    val error = viewModel.error
    val context = LocalContext.current

    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Secundario),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.9f)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Nueva consulta",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                OutlinedTextField(
                    value = fecha.value,
                    onValueChange = { fecha.value = it },
                    label = { Text("Fecha") },
                    placeholder = { Text("dd/mm/aaaa") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = hora.value,
                    onValueChange = { hora.value = it },
                    label = { Text("Hora de inicio") },
                    placeholder = { Text(text =  "(HH:MM)")},
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = paciente,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Paciente") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = medico,
                    readOnly = true,
                    onValueChange = {},
                    label = { Text("Tu Doctor") },
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        disabledTextColor = Color.Black
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(onClick = {
                        val regex = Regex("/api/usuarios/(\\d+)")
                        val matchResult = regex.find(viewModel.user!!.medicoAsignado)

                        val numeroMedico = matchResult?.groups?.get(1)?.value

                        viewModel.postConsulta(
                            fecha = fecha.value,
                            horaInicio = hora.value,
                            medico = numeroMedico!!.toInt(),
                            paciente = viewModel.user!!.id,
                        )


                    }) {
                        Text("Crear")
                    }
                    Button(
                        onClick = { navController.popBackStack() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) {
                        Text("Volver")
                    }
                }
            }
        }
    }
}