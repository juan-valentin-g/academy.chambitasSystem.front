package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("telefono") val telefono: String?,
    @SerializedName("rol") val rol: String,
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName("foto") val foto: String? = null,
    @SerializedName("activo") val activo: Boolean = true,
    @SerializedName(value = "createdAt", alternate = ["created_at"])
    val createdAt: String? = null,
    @SerializedName(value = "updatedAt", alternate = ["updated_at"])
    val updatedAt: String? = null
)

data class UpdateUserStatusRequest(
    @SerializedName("activo") val activo: Boolean
)
