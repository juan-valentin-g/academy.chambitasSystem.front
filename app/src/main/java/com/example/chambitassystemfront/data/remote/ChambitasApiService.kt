package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.AuthResponse
import com.example.chambitassystemfront.data.model.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChambitasApiService {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<AuthResponse>

    // Puedes agregar register aquí mismo después
}