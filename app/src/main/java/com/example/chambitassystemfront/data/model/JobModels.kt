package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

// Mapea la respuesta del backend (GET /jobs)
data class JobResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName(value = "categoryId", alternate = ["category_id"]) val categoryId: Int,
    @SerializedName(value = "ownerId", alternate = ["owner_id", "usuarioId", "userId"]) val ownerId: Int,
    @SerializedName("titulo") val titulo: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("presupuesto") val presupuesto: Double?,
    @SerializedName("ubicacion") val ubicacion: String?,
    @SerializedName("estado") val estado: String,
    @SerializedName(value = "createdAt", alternate = ["created_at"]) val createdAt: String?,
    @SerializedName(value = "updatedAt", alternate = ["updated_at"]) val updatedAt: String?
)

data class UpdateJobDto(
    @SerializedName("categoryId") val categoryId: Int? = null,
    @SerializedName("titulo") val titulo: String? = null,
    @SerializedName("descripcion") val descripcion: String? = null,
    @SerializedName("presupuesto") val presupuesto: Double? = null,
    @SerializedName("ubicacion") val ubicacion: String? = null
)

data class MessageResponseDto(
    @SerializedName("message") val message: String
)
