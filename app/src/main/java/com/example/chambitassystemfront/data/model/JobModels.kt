package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

// Mapea la respuesta del backend (GET /jobs)
data class JobResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("category_id") val categoryId: Int,
    @SerializedName("owner_id", alternate = ["ownerId", "usuarioId", "userId"]) val ownerId: Int,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("presupuesto") val presupuesto: Double,
    @SerializedName("ubicacion") val ubicacion: String,
    @SerializedName("estado") val estado: String,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)