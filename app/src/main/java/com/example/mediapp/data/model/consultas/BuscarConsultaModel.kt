package com.example.mediapp.data.model.consultas

import com.google.gson.annotations.SerializedName

data class BuscarConsultaModel(
    @SerializedName("@context")
    val context: String,
    @SerializedName("@id")
    val id: String,
    @SerializedName("@type")
    val type: String,
    @SerializedName("hydra:member")
    val resultados: List<HydraMemberX>,
    @SerializedName("hydra:totalItems")
    val totalItems: Int,
    @SerializedName("hydra:view")
    val view: HydraViewX
)