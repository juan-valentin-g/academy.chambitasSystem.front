package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.ApplicationResponseDto
import com.example.chambitassystemfront.data.model.MatchResponseDto
import com.example.chambitassystemfront.data.remote.ApiClient
import com.example.chambitassystemfront.data.remote.apiCall

class ApplicationsRepository {

    // Obtener todas las postulaciones del usuario (enviadas)
    suspend fun getMyApplications(): Result<List<ApplicationResponseDto>> = apiCall {
        ApiClient.applicationsApiService.getMyApplications()
    }

    // Obtener solicitudes por ID de trabajo
    suspend fun getApplicationsByJob(jobId: Int): Result<List<ApplicationResponseDto>> = apiCall {
        ApiClient.applicationsApiService.getApplicationsByJob(jobId)
    }

    // Aceptar postulación
    suspend fun acceptApplication(appId: Int): Result<MatchResponseDto> = apiCall {
        ApiClient.applicationsApiService.acceptApplication(appId)
    }

    // Rechazar postulación
    suspend fun rejectApplication(appId: Int): Result<ApplicationResponseDto> = apiCall {
        ApiClient.applicationsApiService.rejectApplication(appId)
    }
}
