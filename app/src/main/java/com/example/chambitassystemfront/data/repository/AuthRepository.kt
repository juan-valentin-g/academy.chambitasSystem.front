package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.LoginRequestDto
import com.example.chambitassystemfront.data.model.LoginResponseDto
import com.example.chambitassystemfront.data.model.RegisterRequestDto
import com.example.chambitassystemfront.data.remote.AuthApiService

class AuthRepository(private val apiService: AuthApiService) {

    suspend fun login(credentials: LoginRequestDto): Result<LoginResponseDto> {
        return try {
            val response = apiService.login(credentials)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(userData: RegisterRequestDto): Result<Unit> {
        return try {
            apiService.register(userData)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}