package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

data class CreateJobDto(
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("presupuesto") val presupuesto: Double?,
    @SerializedName("ubicacion") val ubicacion: String?
)