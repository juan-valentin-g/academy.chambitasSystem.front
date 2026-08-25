package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.CreateJobDto
import com.example.chambitassystemfront.data.model.JobResponseDto
import com.example.chambitassystemfront.data.model.CreateApplicationDto
import com.example.chambitassystemfront.data.model.ApplicationResponseDto
import com.example.chambitassystemfront.data.model.MessageResponseDto
import com.example.chambitassystemfront.data.model.UpdateJobDto
import com.example.chambitassystemfront.data.remote.ApiClient
import com.example.chambitassystemfront.data.remote.apiCall

class JobsRepository {
    suspend fun getJobs(
        titulo: String? = null,
        categoryId: Int? = null,
        ubicacion: String? = null
    ): Result<List<JobResponseDto>> = apiCall {
        ApiClient.jobsApiService.getJobs(titulo, categoryId, ubicacion)
    }

    suspend fun getMyJobs(): Result<List<JobResponseDto>> = apiCall {
        ApiClient.jobsApiService.getMyJobs()
    }

    suspend fun createJob(request: CreateJobDto): Result<JobResponseDto> = apiCall {
        ApiClient.jobsApiService.createJob(request)
    }

    suspend fun getJobById(jobId: Int): Result<JobResponseDto> = apiCall {
        ApiClient.jobsApiService.getJobById(jobId)
    }

    suspend fun updateJob(jobId: Int, request: UpdateJobDto): Result<JobResponseDto> = apiCall {
        ApiClient.jobsApiService.updateJob(jobId, request)
    }

    suspend fun deleteJob(jobId: Int): Result<MessageResponseDto> = apiCall {
        ApiClient.jobsApiService.deleteJob(jobId)
    }

    suspend fun applyToJob(jobId: Int, request: CreateApplicationDto): Result<ApplicationResponseDto> = apiCall {
        ApiClient.applicationsApiService.applyToJob(jobId, request)
    }

    suspend fun getMyApplications(): Result<List<ApplicationResponseDto>> = apiCall {
        ApiClient.applicationsApiService.getMyApplications()
    }
}
