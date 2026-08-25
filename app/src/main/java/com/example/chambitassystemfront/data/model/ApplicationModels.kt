package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

// DTO para postular a una vacante (POST /applications)
data class CreateApplicationDto(
    @SerializedName("mensaje") val mensaje: String? = null
)

data class ApplicationResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName(value = "jobId", alternate = ["job_id"]) val jobId: Int,
    @SerializedName(value = "applicantId", alternate = ["applicant_id", "workerId", "worker_id"])
    val applicantId: Int,
    @SerializedName("estado") val estado: String,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName(value = "createdAt", alternate = ["created_at"]) val createdAt: String,
    @SerializedName(value = "updatedAt", alternate = ["updated_at"]) val updatedAt: String? = null,
    @SerializedName("job") val job: JobResponseDto? = null,
    @SerializedName("applicant") val applicant: UserDto? = null
)
