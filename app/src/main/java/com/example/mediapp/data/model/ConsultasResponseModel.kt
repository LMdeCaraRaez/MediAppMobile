package com.example.mediapp.data.model

import com.google.gson.annotations.SerializedName

data class ConsultasResponseModel(
    @SerializedName("@context")
    val context: String,

    @SerializedName("@id")
    val id: String,

    @SerializedName("@type")
    val type: String,

    @SerializedName("hydra:member")
    val hydraMember: List<HydraMember>,

    @SerializedName("hydra:totalItems")
    val hydraTotalItems: Int,

    @SerializedName("hydra:view")
    val hydraView: HydraView
)