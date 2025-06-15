package com.example.mediapp.ui.screens

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mediapp.viewmodel.TokenViewModel
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VerConsultas(viewModel: TokenViewModel, navController: NavHostController) {
    LaunchedEffect(Unit) {
        viewModel.getConsultas()
    }

    val error = viewModel.error
    val deleteSuccess = viewModel.deleteSuccess
    val context = LocalContext.current
    val scrollState = LazyListState()
    val scope = rememberCoroutineScope()

    val loadedPages = remember {
        mutableIntStateOf(1)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "MediApp - Ver mis consultas",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        }
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color(0xFF1565C0)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                viewModel.buscarConsultasResponse != null -> {

                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        state = scrollState
                    ) {
                        item {
                            Text(
                                text = "Tus Consultas",
                                fontWeight = FontWeight.Bold,
                                fontSize = 32.sp,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                        }

                        items(viewModel.buscarConsultasResponse!!.resultados.size) { index ->
                            val consulta = viewModel.buscarConsultasResponse!!.resultados[index]


                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 8.dp, vertical = 6.dp),
                                elevation = CardDefaults.cardElevation(4.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = "Fecha: ${formatoFechaBonita(consulta.consulta.fecha)}",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = "Horario: ${extraerHora(consulta.consulta.horaInicio)} a ${
                                            extraerHora(
                                                consulta.consulta.horaFin
                                            )
                                        }",
                                        fontSize = 16.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    Spacer(modifier = Modifier.height(6.dp))

                                    if (consulta.consulta.diagnostico != null) {
                                        Text(
                                            text = "Diagnóstico: " + consulta.consulta.diagnostico,
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    } else {
                                        Text(
                                            text = "Consulta no pasada",
                                            fontSize = 16.sp,
                                            color = Color.Red
                                        )
                                        Button(onClick = {
                                            Log.e("consultaID", consulta.consulta.id.toString())

                                            viewModel.deleteConsulta(consulta.consulta.id.toString())
                                        }, modifier = Modifier.padding(top = 12.dp)) {
                                            Text(
                                                text = "Cancelar Consulta",
                                                textAlign = TextAlign.Center,
                                                fontSize = 16.sp,
                                                color = Color.Red,
                                                modifier = Modifier.fillMaxWidth()
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        item {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Button(onClick = {
                                    scope.launch {
                                        scrollState.scrollToItem(0)
                                    }

                                }, modifier = Modifier.padding(bottom = 8.dp)) {
                                    Icon(
                                        imageVector = Icons.Filled.KeyboardArrowUp,
                                        contentDescription = "Volver arriba"
                                    )
                                }

                                if (viewModel.buscarConsultasResponse!!.resultados.size === viewModel.buscarConsultasResponse!!.totalItems) {
                                    Text(
                                        text = "No hay mas resultados",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                } else {
                                    Button(onClick = {
                                        loadedPages.intValue = loadedPages.intValue + 1
                                        viewModel.getConsultas(page = loadedPages.intValue)
                                    }) {
                                        Text(text = "Cargar más")
                                    }
                                }
                            }
                        }
                    }
                }

                viewModel.error != null -> {
                    Text("Error: ${viewModel.error}")
                }

                else -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            LaunchedEffect(error) {
                error?.let {
                    Toast.makeText(context, "Error al intentar borrar la consulta", Toast.LENGTH_LONG).show()
                }
            }

            LaunchedEffect(deleteSuccess) {
                deleteSuccess?.let {
                    if (deleteSuccess === "Consulta borrada correctamente")
                    Toast.makeText(context, "Se ha borrado la consulta, cargando lista de nuevo...", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun extraerHora(fechaIso: String): String {
    val zonedDateTime = ZonedDateTime.parse(fechaIso)
    val formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale("es"))
    return zonedDateTime.format(formatter)
}