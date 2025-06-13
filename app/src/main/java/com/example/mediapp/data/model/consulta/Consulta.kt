package com.example.mediapp.data.model.consulta

import com.google.gson.annotations.SerializedName

data class Consulta(
    @SerializedName("@id")
    val idQuery: String,
    @SerializedName("@type")
    val type: String,
    val diagnostico: String,
    val fecha: String,
    val horaFin: String,
    val horaInicio: String,
    val idConsulta: Int,
    val imagen: List<Any>,
    val medico: String,
    val observaciones: String,
    val paciente: String,
    val receta: List<String>
)