package com.example.chambitassystemfront.data.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val accessToken: String,
    val user: UserDto
)

data class UserDto(
    val id: Int,
    val nombre: String,
    val email: String,
    val telefono: String?,
    val rol: String,
    val descripcion: String?,
    val foto: String?,
    val createdAt: String,
    val updatedAt: String
)