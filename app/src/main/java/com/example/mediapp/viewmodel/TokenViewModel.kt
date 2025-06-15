package com.example.mediapp.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediapp.data.model.RecetasResponseModel
import com.example.mediapp.data.model.UserModelResponse
import com.example.mediapp.data.model.consultas.BuscarConsultaModel
import com.example.mediapp.data.repository.ConsultaRepository
import com.example.mediapp.data.repository.RecetaRepository
import com.example.mediapp.data.repository.TokenRepository
import kotlinx.coroutines.launch

class TokenViewModel : ViewModel() {
    private val repository = TokenRepository()
    private val consultaRepository = ConsultaRepository()
    private val recetaRepository = RecetaRepository()

    var token by mutableStateOf<String?>(null)
        private set

    var refreshToken by mutableStateOf<String?>(null)
        private set

    var recetasResponse by mutableStateOf<RecetasResponseModel?>(null)
        private set
    var buscarConsultasResponse by mutableStateOf<BuscarConsultaModel?>(null)
        private set
    var horasResponse by mutableStateOf<List<String>>(emptyList())

    var error by mutableStateOf<String?>(null)
        private set
    var deleteSuccess by mutableStateOf<String?>(null)
        private set

    var user by mutableStateOf<UserModelResponse?>(null)
        private set

    fun login(username: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(username, password)

                token = response.token
                refreshToken = response.refreshToken
                error = null


                val usuarioResponse = repository.getUsuario(response.token)
                user = usuarioResponse
            } catch (e: Exception) {
                error = "Login fallido: ${e.message}"
            }
        }
    }

    fun postConsulta(paciente: Int, medico: Int, fecha: String, horaInicio: String) {
        viewModelScope.launch {
            try {
                val response = consultaRepository.postConsulta(token!!, paciente, medico, fecha, horaInicio)


            } catch (e: Exception) {
                error = "Login fallido: ${e.message}"
            }
        }
    }

    fun getRecetas(
        orden: String = "fechaConsulta",
        direccion: String = "DESC",
        page: Int = 1
    ) {
        viewModelScope.launch {
            try {
                val response = recetaRepository.getRecetas(token!!, orden, direccion, page)

                if (recetasResponse == null || page == 1) {
                    recetasResponse = response
                } else {
                    val recetasAnteriores = recetasResponse!!.resultados
                    val nuevasRecetas = response.resultados

                    recetasResponse = recetasResponse!!.copy(
                        resultados = recetasAnteriores + nuevasRecetas
                    )
                }


            } catch (e: Exception) {
                error = "Error en recetas: ${e.message}"
            }
        }
    }

    fun getConsultas(
        orden: String = "fecha",
        direccion: String = "DESC",
        page: Int = 1
    ) {
        viewModelScope.launch {
            try {
                val response = consultaRepository.getConsultas(token!!, orden, direccion, page)

                if (buscarConsultasResponse == null || page == 1) {
                    buscarConsultasResponse = response
                } else {
                    val recetasAnteriores = buscarConsultasResponse!!.resultados
                    val nuevasRecetas = response.resultados

                    buscarConsultasResponse = buscarConsultasResponse!!.copy(
                        resultados = recetasAnteriores + nuevasRecetas
                    )
                }


            } catch (e: Exception) {
                error = "Error en consultas: ${e.message}"
            }
        }
    }

    fun getHoras(
        id: String,
        fecha: String
    ) {
        viewModelScope.launch {
            try {
                val response = consultaRepository.getHoras(id, fecha)

                horasResponse = response

                Log.i("HGO", horasResponse.toString())

            } catch (e: Exception) {
                error = "Error en obtenerHoras: ${e.message}"
            }
        }
    }

    fun deleteConsulta(
        id: String
    ) {
        viewModelScope.launch {
            try {
                val response = consultaRepository.deleteConsulta(token!!, id)
                deleteSuccess = "cargando"

                if (response.isSuccessful) {
                    getConsultas()
                    deleteSuccess = "Consulta borrada correctamente"
                }

            } catch (e: Exception) {
                error = "Error borrando la consulta: ${e.message}"

            }
        }
    }
}