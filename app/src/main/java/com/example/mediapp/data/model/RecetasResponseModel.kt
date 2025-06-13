package com.example.mediapp.data.model

import com.example.mediapp.data.model.consulta.HydraView
import com.google.gson.annotations.SerializedName

data class RecetasResponseModel(
    @SerializedName("@context")
    val context: String,
    @SerializedName("@id")
    val idExterno: String,
    @SerializedName("@type")
    val type: String,
    @SerializedName("hydra:member")
    val resultados: List<HydraMemberRecetas>,
    @SerializedName("hydra:totalItems")
    val totalItems: Int,
    @SerializedName("hydra:view")
    val hydraView: HydraView
)

data class HydraMemberRecetas(
    val apellidosMedico: String,
    val apellidosPaciente: String,
    val nombreMedicamento: String,
    val nombreMedico: String,
    val nombrePaciente: String,
    val precio: Int,
    val receta: Receta
)
data class Receta(
    @SerializedName("@id")
    val idExterno: String,
    @SerializedName("@type")
    val type: String,
    val consulta: String,
    val fechaFinTratamiento: String,
    val id: Int,
    val intervalo: Int,
    val medicamento: String
)