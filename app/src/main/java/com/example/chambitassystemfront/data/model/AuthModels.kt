package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequestDto(
    @SerializedName("email") val email: String,          // Cambiado de "correo" a "email"
    @SerializedName("password") val password: String     // Cambiado de "contrasena" a "password"
)

data class LoginResponseDto(
    @SerializedName("access_token") val accessToken: String
)

data class RegisterRequestDto(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email") val email: String,          // Cambiado de "correo" a "email"
    @SerializedName("password") val password: String,    // Cambiado de "contrasena" a "password"
    @SerializedName("telefono") val telefono: String? = null
)