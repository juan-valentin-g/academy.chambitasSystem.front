package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.LoginRequestDto
import com.example.chambitassystemfront.data.model.LoginResponseDto
import com.example.chambitassystemfront.data.model.RegisterRequestDto
import com.example.chambitassystemfront.data.remote.AuthApiService
import com.example.chambitassystemfront.data.remote.apiCall

class AuthRepository(private val apiService: AuthApiService) {

    suspend fun login(credentials: LoginRequestDto): Result<LoginResponseDto> = apiCall {
        apiService.login(credentials)
    }

    suspend fun register(userData: RegisterRequestDto): Result<LoginResponseDto> = apiCall {
        apiService.register(userData)
    }
}
