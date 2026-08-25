package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

// DTO para postular a una vacante (POST /applications)
data class CreateApplicationDto(
    @SerializedName("mensaje") val mensaje: String? = null
)

// Respuesta al consultar postulaciones con soporte para ambos nombres de campo (job_id o jobId)
data class ApplicationResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName(value = "job_id", alternate = ["jobId"]) val jobId: Int,
    @SerializedName(value = "worker_id", alternate = ["workerId"]) val workerId: Int,
    @SerializedName("estado") val estado: String,
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName(value = "created_at", alternate = ["createdAt"]) val createdAt: String
)