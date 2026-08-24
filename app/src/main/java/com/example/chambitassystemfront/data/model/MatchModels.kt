package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

data class MatchResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("application_id") val applicationId: Int,
    @SerializedName("job_id") val jobId: Int,
    @SerializedName("employer_id") val employerId: Int,
    @SerializedName("worker_id") val workerId: Int,
    @SerializedName("estado") val estado: String, // 'ACTIVO', 'FINALIZADO', 'CANCELADO'
    @SerializedName("started_at") val startedAt: String,
    @SerializedName("completed_at") val completedAt: String?
)

data class CreateMatchDto(
    @SerializedName("application_id") val applicationId: Int,
    @SerializedName("job_id") val jobId: Int,
    @SerializedName("employer_id") val employerId: Int,
    @SerializedName("worker_id") val workerId: Int
)