package com.example.mediapp.data.remote

import com.example.mediapp.data.model.consulta.ConsultasRequest
import com.example.mediapp.data.model.consulta.ConsultasResponseModel
import com.example.mediapp.data.model.RecetasResponseModel
import com.example.mediapp.data.model.TokenRequest
import com.example.mediapp.data.model.TokenResponse
import com.example.mediapp.data.model.UserModelResponse
import com.example.mediapp.data.model.consulta.HorasResponseModel
import com.example.mediapp.data.model.consultas.BuscarConsultaModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @POST("security_login")
    suspend fun login(@Body request: TokenRequest): TokenResponse

    @GET("api/usuario")
    suspend fun getUsuario(@Header("Authorization") token: String): UserModelResponse

    @POST("api/consultas")
    suspend fun postConsulta(
        @Header("Authorization") token: String,
        @Body request: ConsultasRequest
    ): ConsultasResponseModel

    @GET("api/recetas")
    suspend fun getRecetas(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("orden") orden: String,
        @Query("propias") propias: String,
        @Query("direccion") direccion: String
    ): RecetasResponseModel

    @GET("api/consultas")
    suspend fun getConsultas(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("orden") orden: String,
        @Query("propias") propias: String,
        @Query("direccion") direccion: String
    ): BuscarConsultaModel

    @DELETE("api/consultas/{id}")
    suspend fun deleteConsulta(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<ConsultasResponseModel>

    @GET("api/usuarios/horas_disponibles/{id}")
    suspend fun getHorasDisponibles(
        @Path("id") id: String,
        @Query("fecha") fecha: String,
    ): HorasResponseModel
}