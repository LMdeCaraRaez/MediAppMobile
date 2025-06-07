package com.example.mediapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mediapp.data.model.UserModelResponse
import com.example.mediapp.data.repository.TokenRepository
import kotlinx.coroutines.launch

class TokenViewModel: ViewModel() {
    private val repository = TokenRepository()

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
}