package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.UserResponseDto
import com.example.chambitassystemfront.data.model.UpdateUserStatusRequest
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH

interface UsersApiService {

    @GET("users")
    suspend fun getUsers(): List<UserResponseDto>

    @GET("users/profile")
    suspend fun getProfile(): UserResponseDto

    @PATCH("users/profile")
    suspend fun updateProfile(
        @Body request: Map<String, String?>
    ): UserResponseDto

    @PATCH("users/{id}/status")
    suspend fun updateUserStatus(
        @Path("id") userId: Int,
        @Body request: UpdateUserStatusRequest
    ): UserResponseDto
}
