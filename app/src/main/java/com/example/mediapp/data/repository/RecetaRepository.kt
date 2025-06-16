package com.example.mediapp.data.repository

import com.example.mediapp.data.model.RecetasResponseModel
import com.example.mediapp.data.remote.RetrofitClient

class RecetaRepository {
    suspend fun getRecetas(
        token: String,
        orden: String,
        propias: String,
        direccion: String,
        page: Int
    ): RecetasResponseModel {
        return RetrofitClient.api.getRecetas("Bearer $token", page, orden, propias, direccion)
    }
}