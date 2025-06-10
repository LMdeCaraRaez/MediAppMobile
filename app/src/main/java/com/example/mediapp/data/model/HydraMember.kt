package com.example.mediapp.data.model

data class HydraMember(
    val apellidosMedico: String,
    val apellidosPaciente: String,
    val consulta: Consulta,
    val nombreMedico: String,
    val nombrePaciente: String
)