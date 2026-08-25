package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.UserResponseDto
import com.example.chambitassystemfront.data.model.UpdateUserStatusRequest
import com.example.chambitassystemfront.data.remote.ApiClient
import com.example.chambitassystemfront.data.remote.apiCall

class UserRepository {
    suspend fun getUsers(): Result<List<UserResponseDto>> = apiCall {
        ApiClient.usersApiService.getUsers()
    }

    suspend fun getProfile(): Result<UserResponseDto> = apiCall {
        ApiClient.usersApiService.getProfile()
    }

    suspend fun updateProfile(name: String, phone: String, description: String): Result<UserResponseDto> = apiCall {
            val body = mapOf(
                "nombre" to name,
                "telefono" to phone,
                "descripcion" to description
            )
            ApiClient.usersApiService.updateProfile(body)
    }

    suspend fun updateUserStatus(
        userId: Int,
        active: Boolean
    ): Result<UserResponseDto> = apiCall {
        ApiClient.usersApiService.updateUserStatus(
            userId,
            UpdateUserStatusRequest(active)
        )
    }
}
