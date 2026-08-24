package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.ApplicationResponseDto
import com.example.chambitassystemfront.data.remote.ApiClient

class ApplicationsRepository {

    suspend fun getSentApplications(token: String): Result<List<ApplicationResponseDto>> {
        return try {
            val response = ApiClient.applicationsApiService.getSentApplications(token)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun getReceivedApplications(token: String): Result<List<ApplicationResponseDto>> {
        return try {
            val response = ApiClient.applicationsApiService.getReceivedApplications(token)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun updateStatus(token: String, appId: Int, newStatus: String): Result<ApplicationResponseDto> {
        return try {
            val body = mapOf("estado" to newStatus)
            val response = ApiClient.applicationsApiService.updateApplicationStatus(token, appId, body)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}