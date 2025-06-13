package com.example.mediapp.data.repository

import com.example.mediapp.data.model.RecetasResponseModel
import com.example.mediapp.data.model.consulta.ConsultasRequest
import com.example.mediapp.data.model.consulta.ConsultasResponseModel
import com.example.mediapp.data.model.consultas.BuscarConsultaModel
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

    suspend fun getConsultas(
        token: String,
        orden: String,
        direccion: String,
        page: Int
    ): BuscarConsultaModel {
        return RetrofitClient.api.getConsultas("Bearer $token", page, orden, direccion)
    }
}