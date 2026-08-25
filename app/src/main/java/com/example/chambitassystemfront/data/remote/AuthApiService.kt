package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.LoginRequestDto
import com.example.chambitassystemfront.data.model.LoginResponseDto
import com.example.chambitassystemfront.data.model.RegisterRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("auth/login")
    suspend fun login(@Body credentials: LoginRequestDto): LoginResponseDto

    @POST("auth/register")
    suspend fun register(@Body userData: RegisterRequestDto): LoginResponseDto
}
