package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.LoginRequest
import com.example.chambitassystemfront.data.model.AuthResponse
import com.example.chambitassystemfront.data.model.RegisterRequestDto // O el nombre que tenga tu modelo de registro
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    suspend fun login(@Body credentials: LoginRequest): AuthResponse

    @POST("auth/register")
    suspend fun register(@Body userData: RegisterRequestDto)
}