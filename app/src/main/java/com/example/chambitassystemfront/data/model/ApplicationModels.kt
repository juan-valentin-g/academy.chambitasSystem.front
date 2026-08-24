package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

// DTO para postular a una vacante (POST /applications) -> Se eliminó jobId porque va en la URL
data class CreateApplicationDto(
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