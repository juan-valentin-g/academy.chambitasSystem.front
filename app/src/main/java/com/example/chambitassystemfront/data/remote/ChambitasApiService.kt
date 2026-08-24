package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.LoginRequestDto
import com.example.chambitassystemfront.data.model.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChambitasApiService {

    @POST("auth/login")
    suspend fun login(
        @Body request: LoginRequestDto
    ): Response<LoginResponseDto>

}