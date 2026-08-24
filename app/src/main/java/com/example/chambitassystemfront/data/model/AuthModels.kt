package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequestDto(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)


// Unificamos la respuesta del login para que use exactamente "accessToken" como manda NestJS
data class LoginResponseDto(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("user") val user: UserDto? = null
)

data class RegisterRequestDto(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("telefono") val telefono: String? = null
)

data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,
    @SerializedName("telefono") val telefono: String?,
    @SerializedName("rol") val rol: String,
    @SerializedName("descripcion") val descripcion: String?,
    @SerializedName("foto") val foto: String?,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("updatedAt") val updatedAt: String


)