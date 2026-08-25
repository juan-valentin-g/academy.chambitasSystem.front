package com.example.chambitassystemfront.ui.screens.jobs

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chambitassystemfront.data.model.CreateJobDto
import com.example.chambitassystemfront.data.model.CreateApplicationDto
import com.example.chambitassystemfront.data.model.JobResponseDto
import com.example.chambitassystemfront.data.repository.JobsRepository
import kotlinx.coroutines.launch

class JobViewModel(private val jobsRepository: JobsRepository) : ViewModel() {

    private var allJobs: List<JobResponseDto> = emptyList()

    var jobs by mutableStateOf<List<JobResponseDto>>(emptyList())
        private set

    var myPublications by mutableStateOf<List<JobResponseDto>>(emptyList())
        private set

    var myApplications by mutableStateOf<List<JobResponseDto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    private val appliedJobIds = mutableSetOf<Int>()
    private var currentUserId: Int = 0

    fun setCurrentUserId(userId: Int) {
        currentUserId = userId
        applyFilters()
    }

    fun markAsApplied(jobId: Int) {
        appliedJobIds.add(jobId)
        applyFilters()
    }

    private fun applyFilters() {
        // 1. Mis publicaciones (creadas por mí)
        myPublications = allJobs.filter { it.ownerId == currentUserId }

        // 2. Trabajos a los que ya me postulé
        myApplications = allJobs.filter { appliedJobIds.contains(it.id) }

        // 3. Bolsa general de inicio y búsqueda:
        // Excluimos estrictamente los míos (ownerId != currentUserId), los ya aplicados y exigimos que estén PUBLICADOS.
        jobs = allJobs.filter { job ->
            val isNotMine = if (currentUserId > 0) job.ownerId != currentUserId else false
            val notApplied = !appliedJobIds.contains(job.id)
            val isPublished = job.estado.uppercase() == "PUBLICADO"

            isNotMine && notApplied && isPublished
        }
    }

    fun fetchJobs(token: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            val authHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"

            try {
                val result = jobsRepository.getJobs(authHeader)
                result.fold(
                    onSuccess = { list ->
                        allJobs = list
                        applyFilters()
                    },
                    onFailure = { error ->
                        errorMessage = error.message
                    }
                )
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
            } finally {
                isLoading = false
            }
        }
    }

    fun createJob(
        token: String,
        categoryId: Int,
        titulo: String,
        descripcion: String,
        presupuesto: Double?,
        ubicacion: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            val authHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"
            val request = CreateJobDto(
                categoryId = categoryId,
                titulo = titulo,
                descripcion = descripcion,
                presupuesto = presupuesto ?: 0.0,
                ubicacion = ubicacion ?: ""
            )

            try {
                val result = jobsRepository.createJob(authHeader, request)
                result.fold(
                    onSuccess = { createdJob ->
                        allJobs = listOf(createdJob) + allJobs
                        applyFilters()
                        onSuccess()
                    },
                    onFailure = { error ->
                        onError(error.message ?: "Error al crear el trabajo")
                    }
                )
            } catch (e: Exception) {
                onError(e.localizedMessage ?: "Error inesperado")
            } finally {
                isLoading = false
            }
        }
    }

    fun applyToJob(
        token: String,
        jobId: Int,
        mensaje: String,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            val authHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"

            try {
                val request = CreateApplicationDto(mensaje = mensaje)
                val result = jobsRepository.applyToJob(authHeader, jobId, request)

                result.fold(
                    onSuccess = {
                        markAsApplied(jobId)
                        onSuccess()
                    },
                    onFailure = { error ->
                        onError(error.message ?: "Error al postularse")
                    }
                )
            } catch (e: Exception) {
                onError("Error de conexión: ${e.localizedMessage}")
            } finally {
                isLoading = false
            }
        }
    }
}