package com.example.mediapp.data.model

import com.google.gson.annotations.SerializedName

data class UserModelResponse(
    @SerializedName("@context")
    val context: String,
    @SerializedName("@id")
    val idExterno: String,
    @SerializedName("@type")
    val type: String,
    val admin: Boolean,
    val altura: Int,
    val apellidos: String,
    val consultas: List<String>,
    val consultasComoMedico: List<String>,
    val dni: String,
    val doctor: Boolean,
    val duracionConsultas: Int,
    val email: String,
    val finTurno: String,
    val genero: String,
    val id: Int, // Renombrado para evitar error con id ya usado
    val medicoAsignado: String, // Renombrado para evitar error con id ya usado
    val inicioTurno: String,
    val nombre: String,
    val pacientes: List<String>,
    val peso: Int,
    val roles: List<String>,
    val telefono: String,
    val userIdentifier: String,
    val username: String
)