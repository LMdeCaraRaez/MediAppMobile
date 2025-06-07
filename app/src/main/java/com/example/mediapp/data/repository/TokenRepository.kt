package com.example.mediapp.data.repository

import com.example.mediapp.data.model.TokenRequest
import com.example.mediapp.data.model.TokenResponse
import com.example.mediapp.data.model.UserModelResponse
import com.example.mediapp.data.remote.RetrofitClient

class TokenRepository {
    suspend fun login(username: String, password: String): TokenResponse {
        val request = TokenRequest(username, password)
        return RetrofitClient.api.login(request)
    }

    suspend fun getUsuario(token: String): UserModelResponse {
        return RetrofitClient.api.getUsuario("Bearer $token")
    }
}