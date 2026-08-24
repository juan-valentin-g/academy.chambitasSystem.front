package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.CreateJobDto
import com.example.chambitassystemfront.data.model.JobResponseDto
import retrofit2.http.*

interface JobsApiService {

    @GET("jobs")
    suspend fun getJobs(
        @Header("Authorization") token: String,
        @Query("search") search: String? = null,
        @Query("categoryId") categoryId: Int? = null
    ): List<JobResponseDto>

    @GET("jobs/{id}")
    suspend fun getJobById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): JobResponseDto

    @POST("jobs")
    suspend fun createJob(
        @Header("Authorization") token: String,
        @Body job: CreateJobDto
    ): JobResponseDto
}