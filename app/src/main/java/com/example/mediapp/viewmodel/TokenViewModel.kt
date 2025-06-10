package com.example.mediapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediapp.data.model.UserModelResponse
import com.example.mediapp.data.repository.ConsultaRepository
import com.example.mediapp.data.repository.TokenRepository
import kotlinx.coroutines.launch

class TokenViewModel : ViewModel() {
    private val repository = TokenRepository()
    private val consultaRepository = ConsultaRepository()

    var token by mutableStateOf<String?>(null)
        private set

    var refreshToken by mutableStateOf<String?>(null)
        private set

    var error by mutableStateOf<String?>(null)
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
                val response = consultaRepository.postConsulta(token!! ,paciente, medico, fecha, horaInicio)

            } catch (e: Exception) {
                error = "Login fallido: ${e.message}"
            }
        }
    }
}