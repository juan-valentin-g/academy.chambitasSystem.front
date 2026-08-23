package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

// DTO para postular a una vacante (POST /applications)
data class CreateApplicationDto(
    @SerializedName("job_id") val jobId: Int,
    @SerializedName("mensaje") val mensaje: String? = null
)

// Respuesta al consultar postulaciones
data class ApplicationResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("job_id") val jobId: Int,
    @SerializedName("worker_id") val workerId: Int,
    @SerializedName("estado") val estado: String, // 'PENDIENTE', 'ACEPTADA', 'RECHAZADA'
    @SerializedName("mensaje") val mensaje: String?,
    @SerializedName("created_at") val createdAt: String
)