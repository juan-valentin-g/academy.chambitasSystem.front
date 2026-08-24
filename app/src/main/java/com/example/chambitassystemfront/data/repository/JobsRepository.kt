package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.CreateJobDto
import com.example.chambitassystemfront.data.model.JobResponseDto
import com.example.chambitassystemfront.data.model.CreateApplicationDto
import com.example.chambitassystemfront.data.model.ApplicationResponseDto
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

    suspend fun getJobById(token: String, jobId: Int): Result<JobResponseDto> {
        return try {
            val response = ApiClient.jobsApiService.getJobById(token, jobId)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun applyToJob(token: String, jobId: Int, request: CreateApplicationDto): Result<ApplicationResponseDto> {
        return try {
            val response = ApiClient.applicationsApiService.applyToJob(token, jobId, request)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}