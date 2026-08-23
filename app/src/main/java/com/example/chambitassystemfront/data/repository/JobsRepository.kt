package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.CreateJobDto
import com.example.chambitassystemfront.data.model.JobResponseDto
import com.example.chambitassystemfront.data.remote.JobsApiService

class JobsRepository(private val apiService: JobsApiService) {

    suspend fun getJobs(search: String? = null, categoryId: Int? = null): Result<List<JobResponseDto>> {
        return try {
            val response = apiService.getJobs(search, categoryId)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getJobById(id: Int): Result<JobResponseDto> {
        return try {
            val response = apiService.getJobById(id)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createJob(job: CreateJobDto): Result<JobResponseDto> {
        return try {
            val response = apiService.createJob(job)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}