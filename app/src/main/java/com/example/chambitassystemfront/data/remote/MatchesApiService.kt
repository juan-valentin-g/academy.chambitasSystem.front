package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.CreateApplicationDto
import com.example.chambitassystemfront.data.model.ApplicationResponseDto
import com.example.chambitassystemfront.data.model.MatchResponseDto
import retrofit2.http.*

interface MatchesApiService {

    @POST("applications")
    suspend fun applyToJob(@Body application: CreateApplicationDto): ApplicationResponseDto

    @GET("applications")
    suspend fun getApplications(): List<ApplicationResponseDto>

    @GET("matches")
    suspend fun getMatches(): List<MatchResponseDto>
}