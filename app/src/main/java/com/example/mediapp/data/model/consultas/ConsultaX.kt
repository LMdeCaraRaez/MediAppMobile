package com.example.mediapp.data.model.consultas

import com.google.gson.annotations.SerializedName

data class ConsultaX(
    @SerializedName("@id")
    val idExterno: String,
    @SerializedName("@type")
    val type: String,
    val diagnostico: String,
    val fecha: String,
    val horaFin: String,
    val horaInicio: String,
    val id: Int,
    val imagen: List<String>,
    val medico: String,
    val observaciones: String,
    val paciente: String,
    val receta: List<String>
)