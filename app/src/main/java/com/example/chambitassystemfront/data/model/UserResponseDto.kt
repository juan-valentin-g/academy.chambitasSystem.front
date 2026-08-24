package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

data class UserResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("telefono") val telefono: String?,
    @SerializedName("descripcion") val descripcion: String?
)