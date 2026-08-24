package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.UserResponseDto
import com.example.chambitassystemfront.data.remote.ApiClient

class UserRepository {
    suspend fun getProfile(token: String): Result<UserResponseDto> {
        return try {
            val response = ApiClient.usersApiService.getProfile(token)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun updateProfile(token: String, name: String, phone: String, description: String): Result<UserResponseDto> {
        return try {
            val body = mapOf(
                "nombre" to name,
                "telefono" to phone,
                "descripcion" to description
            )
            val response = ApiClient.usersApiService.updateProfile(token, body)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}