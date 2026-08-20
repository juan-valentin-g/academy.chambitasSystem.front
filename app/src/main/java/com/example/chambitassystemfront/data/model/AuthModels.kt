package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

data class LoginRequestDto(
    @SerializedName("correo") val correo: String,
    @SerializedName("contrasena") val contrasena: String
)

data class LoginResponseDto(
    @SerializedName("access_token") val accessToken: String
)

data class RegisterRequestDto(
    @SerializedName("nombre") val nombre: String,
    @SerializedName("correo") val correo: String,
    @SerializedName("contrasena") val contrasena: String,
    @SerializedName("telefono") val telefono: String? = null
)