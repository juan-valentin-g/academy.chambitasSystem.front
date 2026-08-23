package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

// Mapea la relación de contrato/match (GET /matches)
data class MatchResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("job_id") val jobId: Int,
    @SerializedName("worker_id") val workerId: Int,
    @SerializedName("estado") val estado: String,
    @SerializedName("created_at") val createdAt: String
)