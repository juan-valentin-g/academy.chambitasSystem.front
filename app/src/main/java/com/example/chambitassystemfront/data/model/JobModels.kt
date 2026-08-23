package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

// Mapea la respuesta del backend (GET /jobs)
data class JobResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("owner_id") val ownerId: Int,
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("presupuesto") val presupuesto: Double,
    @SerializedName("ubicacion") val ubicacion: String,
    @SerializedName("estado") val estado: String,
    @SerializedName("created_at") val createdAt: String
)

// DTO para crear un nuevo trabajo (POST /jobs)
data class CreateJobDto(
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("presupuesto") val presupuesto: Double,
    @SerializedName("ubicacion") val ubicacion: String
)