package com.example.mediapp.data.model.consulta

data class ConsultasRequest (
    val paciente: Int,
    val medico: Int,
    val fecha: String,
    val horaInicio: String,
)

