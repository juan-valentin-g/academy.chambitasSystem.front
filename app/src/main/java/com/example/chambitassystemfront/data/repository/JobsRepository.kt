package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.CreateJobDto
import com.example.chambitassystemfront.data.model.JobResponseDto
import com.example.chambitassystemfront.data.remote.ApiClient

class JobsRepository {
    suspend fun getJobs(token: String): Result<List<JobResponseDto>> {
        return try {
            val response = ApiClient.jobsApiService.getJobs(token)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun createJob(token: String, request: CreateJobDto): Result<JobResponseDto> {
        return try {
            val response = ApiClient.jobsApiService.createJob(token, request)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}