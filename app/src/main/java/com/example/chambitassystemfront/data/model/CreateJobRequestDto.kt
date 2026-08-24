package com.example.chambitassystemfront.data.model

data class CreateJobRequestDto(
    val categoryId: Int,
    val titulo: String,
    val descripcion: String,
    val presupuesto: Double?,
    val ubicacion: String?
)