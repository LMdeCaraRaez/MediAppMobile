package com.example.mediapp.data.model.consultas

import com.google.gson.annotations.SerializedName

data class HydraViewX(
    @SerializedName("@id")
    val id: String,
    @SerializedName("@type")
    val type: String,
    @SerializedName("hydra:first")
    val first: String,
    @SerializedName("hydra:last")
    val last: String,
    @SerializedName("hydra:next")
    val next: String,
)