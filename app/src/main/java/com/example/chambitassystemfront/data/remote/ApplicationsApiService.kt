package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.ApplicationResponseDto
import com.example.chambitassystemfront.data.model.CreateApplicationDto
import com.example.chambitassystemfront.data.model.MatchResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ApplicationsApiService {

    // 🚀 NUEVO: Endpoint para obtener las postulaciones enviadas del usuario
    @GET("applications/my")
    suspend fun getMyApplications(): List<ApplicationResponseDto>

    @POST("jobs/{jobId}/applications")
    suspend fun applyToJob(
        @Path("jobId") jobId: Int,
        @Body request: CreateApplicationDto
    ): ApplicationResponseDto

    @GET("jobs/{jobId}/applications")
    suspend fun getApplicationsByJob(
        @Path("jobId") jobId: Int
    ): List<ApplicationResponseDto>

    @PATCH("applications/{id}/accept")
    suspend fun acceptApplication(
        @Path("id") applicationId: Int
    ): MatchResponseDto

    @PATCH("applications/{id}/reject")
    suspend fun rejectApplication(
        @Path("id") applicationId: Int
    ): ApplicationResponseDto
}
