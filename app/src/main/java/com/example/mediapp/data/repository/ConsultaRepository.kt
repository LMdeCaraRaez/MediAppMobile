package com.example.mediapp.data.repository

import com.example.mediapp.data.model.ConsultasRequest
import com.example.mediapp.data.model.ConsultasResponseModel
import com.example.mediapp.data.remote.RetrofitClient
class ConsultaRepository {
    suspend fun postConsulta(
        token: String,
        paciente: Int,
        medico: Int,
        fecha: String,
        horaInicio: String
    ): ConsultasResponseModel {
        val request = ConsultasRequest(paciente, medico, fecha, horaInicio)
        return RetrofitClient.api.postConsulta("Bearer $token", request)
    }
}