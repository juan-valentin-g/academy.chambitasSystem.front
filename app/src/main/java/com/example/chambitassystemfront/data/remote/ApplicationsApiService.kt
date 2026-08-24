package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.ApplicationResponseDto
import com.example.chambitassystemfront.data.model.CreateApplicationDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApplicationsApiService {

    // 💡 Método que ya tenías para postularte a una chamba
    @POST("jobs/{jobId}/applications")
    suspend fun applyToJob(
        @Header("Authorization") token: String,
        @Path("jobId") jobId: Int,
        @Body request: CreateApplicationDto
    ): ApplicationResponseDto

    // 💡 NUEVO: Obtener las postulaciones enviadas por el usuario actual
    @GET("applications/sent")
    suspend fun getSentApplications(
        @Header("Authorization") token: String
    ): List<ApplicationResponseDto>

    // 💡 NUEVO: Obtener las postulaciones recibidas en los trabajos del usuario
    @GET("applications/received")
    suspend fun getReceivedApplications(
        @Header("Authorization") token: String
    ): List<ApplicationResponseDto>

    // 💡 NUEVO: Actualizar el estado de una postulación (Aceptar / Rechazar)
    @PUT("applications/{id}/status")
    suspend fun updateApplicationStatus(
        @Header("Authorization") token: String,
        @Path("id") applicationId: Int,
        @Body body: Map<String, String>
    ): ApplicationResponseDto
}