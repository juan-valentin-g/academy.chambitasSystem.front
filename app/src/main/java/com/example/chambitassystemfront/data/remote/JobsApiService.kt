package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.CreateJobDto
import com.example.chambitassystemfront.data.model.JobResponseDto
import com.example.chambitassystemfront.data.model.MessageResponseDto
import com.example.chambitassystemfront.data.model.UpdateJobDto
import retrofit2.http.*

interface JobsApiService {

    @GET("jobs")
    suspend fun getJobs(
        @Query("titulo") titulo: String? = null,
        @Query("categoryId") categoryId: Int? = null,
        @Query("ubicacion") ubicacion: String? = null
    ): List<JobResponseDto>

    @GET("jobs/mine")
    suspend fun getMyJobs(): List<JobResponseDto>

    @GET("jobs/{id}")
    suspend fun getJobById(
        @Path("id") id: Int
    ): JobResponseDto

    @POST("jobs")
    suspend fun createJob(
        @Body job: CreateJobDto
    ): JobResponseDto

    @PATCH("jobs/{id}")
    suspend fun updateJob(
        @Path("id") id: Int,
        @Body job: UpdateJobDto
    ): JobResponseDto

    @DELETE("jobs/{id}")
    suspend fun deleteJob(@Path("id") id: Int): MessageResponseDto
}
