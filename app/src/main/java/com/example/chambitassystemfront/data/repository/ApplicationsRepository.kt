package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.ApplicationResponseDto
import com.example.chambitassystemfront.data.remote.ApiClient

class ApplicationsRepository {

    // Obtener todas las postulaciones del usuario (enviadas)
    suspend fun getMyApplications(token: String): Result<List<ApplicationResponseDto>> {
        return try {
            val response = ApiClient.applicationsApiService.getMyApplications(token)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    // Obtener solicitudes por ID de trabajo
    suspend fun getApplicationsByJob(token: String, jobId: Int): Result<List<ApplicationResponseDto>> {
        return try {
            val response = ApiClient.applicationsApiService.getApplicationsByJob(token, jobId)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    // Aceptar postulación
    suspend fun acceptApplication(token: String, appId: Int): Result<ApplicationResponseDto> {
        return try {
            val response = ApiClient.applicationsApiService.acceptApplication(token, appId)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    // Rechazar postulación
    suspend fun rejectApplication(token: String, appId: Int): Result<ApplicationResponseDto> {
        return try {
            val response = ApiClient.applicationsApiService.rejectApplication(token, appId)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}