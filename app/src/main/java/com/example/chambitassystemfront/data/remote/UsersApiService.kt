package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.UserResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

interface UsersApiService {

    @GET("auth/profile") // O la ruta de tu API en NestJS para obtener el usuario autenticado
    suspend fun getProfile(
        @Header("Authorization") token: String
    ): UserResponseDto

    @PUT("auth/profile") // O la ruta correspondiente para actualizar
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: Map<String, String?>
    ): UserResponseDto
}