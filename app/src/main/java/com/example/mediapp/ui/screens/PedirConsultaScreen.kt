package com.example.mediapp.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mediapp.ui.theme.Secundario
import com.example.mediapp.viewmodel.TokenViewModel
import java.time.LocalDate


@SuppressLint("NewApi")
@Composable
fun PedirConsultaScreen(viewModel: TokenViewModel, navController: NavHostController) {
    val fecha = remember { mutableStateOf("") }
    val hora = remember { mutableStateOf("") }
    val regex = Regex("/api/usuarios/(\\d+)")
    val matchResult = regex.find(viewModel.user!!.medicoAsignado)
    val numeroMedico = matchResult?.groups?.get(1)?.value
    val paciente = "${viewModel.user?.nombre} ${viewModel.user?.apellidos}"
    val horas = viewModel.horasResponse
    val medico = "Tu doctor"
    val error = viewModel.error
    val context = LocalContext.current
    val postConsultaSuccess = viewModel.postConsultaSuccess
    var mostrarErrorFecha by remember { mutableStateOf(false) }
    val formatoCorrecto = remember { Regex("""\d{4}-\d{2}-\d{2}""") }
    val hoy = remember { LocalDate.now() }
    var consultaPedida = false

    LaunchedEffect(error) {
        error?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            consultaPedida = false
        }
    }

    // Solo se llama cuando la fecha es correcta para buscar las fechas del doctor
    LaunchedEffect(fecha.value, mostrarErrorFecha) {
        if (!mostrarErrorFecha && fecha.value.isNotEmpty()) {
            viewModel.getHoras(
                numeroMedico!!,
                fecha.value
            )
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
                    onValueChange = {
                        viewModel.horasResponse = listOf()
                        hora.value = ""
                        fecha.value = it
                        mostrarErrorFecha = try {
                            if (!formatoCorrecto.matches(it)) {
                                true
                            } else {
                                val fechaIngresada = LocalDate.parse(it)
                                fechaIngresada.isBefore(hoy)
                            }
                        } catch (e: Exception) {
                            true
                        }
                    },
                    label = { Text("Fecha") },
                    placeholder = { Text("YYYY-MM-DD") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = mostrarErrorFecha
                )

                if (mostrarErrorFecha) {
                    Text(
                        text = "La fecha debe tener formato YYYY-MM-DD y ser al menos hoy",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                SimpleDropdownMenu(horas = horas,
                    selectedHora = hora.value,
                    onHoraSeleccionada = { hora.value = it }
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

                        if (!consultaPedida) {
                            consultaPedida = true
                            if (mostrarErrorFecha || hora.value == "") {
                                Toast.makeText(
                                    context,
                                    "Escribe unas fecha y hora correctas por favor",
                                    Toast.LENGTH_LONG
                                ).show()
                                consultaPedida = false
                            } else {
                                viewModel.postConsulta(
                                    fecha = fecha.value,
                                    horaInicio = hora.value,
                                    medico = numeroMedico!!.toInt(),
                                    paciente = viewModel.user!!.id,
                                )
                            }
                        }


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

    LaunchedEffect(postConsultaSuccess) {
        postConsultaSuccess?.let {
            if (postConsultaSuccess === "Se ha creado la consulta") {
                Toast.makeText(context, "Se ha creado la consulta", Toast.LENGTH_LONG).show()
                navController.popBackStack()
            } else if (postConsultaSuccess === "cargando") {
                Toast.makeText(context, "Cargando", Toast.LENGTH_LONG).show()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimpleDropdownMenu(
    horas: List<String>,
    selectedHora: String,
    onHoraSeleccionada: (String) -> Unit
) {
    val options = if (horas.isEmpty()) listOf("No hay horas disponibles") else horas

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        TextField(
            value = selectedHora,
            onValueChange = {},
            readOnly = true,
            label = { Text("Horas disponibles") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            enabled = horas.isNotEmpty()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onHoraSeleccionada(selectionOption)
                        expanded = false
                    }
                )
            }
        }
    }
}