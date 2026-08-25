package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

data class MatchResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName(value = "applicationId", alternate = ["application_id"])
    val applicationId: Int,
    @SerializedName(value = "jobId", alternate = ["job_id"]) val jobId: Int,
    @SerializedName(value = "employerId", alternate = ["employer_id"]) val employerId: Int,
    @SerializedName(value = "workerId", alternate = ["worker_id"]) val workerId: Int,
    @SerializedName("estado") val estado: String,
    @SerializedName(value = "startedAt", alternate = ["started_at"]) val startedAt: String?,
    @SerializedName(value = "completedAt", alternate = ["completed_at"]) val completedAt: String?,
    @SerializedName(value = "createdAt", alternate = ["created_at"]) val createdAt: String? = null,
    @SerializedName(value = "updatedAt", alternate = ["updated_at"]) val updatedAt: String? = null,
    @SerializedName("job") val job: JobResponseDto? = null,
    @SerializedName("application") val application: ApplicationResponseDto? = null,
    @SerializedName("employer") val employer: UserDto? = null,
    @SerializedName("worker") val worker: UserDto? = null
)
