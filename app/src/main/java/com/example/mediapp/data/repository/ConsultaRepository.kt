package com.example.mediapp.data.repository

import com.example.mediapp.data.model.consulta.ConsultasRequest
import com.example.mediapp.data.model.consulta.ConsultasResponseModel
import com.example.mediapp.data.model.consulta.HorasResponseModel
import com.example.mediapp.data.model.consultas.BuscarConsultaModel
import com.example.mediapp.data.remote.RetrofitClient
import retrofit2.Response
import retrofit2.http.Query

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
        propias: String,
        page: Int
    ): BuscarConsultaModel {
        return RetrofitClient.api.getConsultas("Bearer $token", page, orden, propias, direccion)
    }

    suspend fun getHoras(
        id: String,
        fecha: String
    ): HorasResponseModel {
        return RetrofitClient.api.getHorasDisponibles(id, fecha)
    }

    suspend fun deleteConsulta(
        token: String,
        id: String,
    ): Response<ConsultasResponseModel> {
        return RetrofitClient.api.deleteConsulta("Bearer $token", id)
    }
}