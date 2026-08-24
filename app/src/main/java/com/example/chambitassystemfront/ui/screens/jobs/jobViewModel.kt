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

    var jobs by mutableStateOf<List<JobResponseDto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchJobs(token: String) {
        Log.d("JobViewModel", "-> fetchJobs llamado con token: $token")
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val authHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"

            try {
                val result = jobsRepository.getJobs(authHeader)
                result.fold(
                    onSuccess = { list ->
                        Log.d("JobViewModel", "-> Trabajos obtenidos con éxito: ${list.size}")
                        jobs = list
                    },
                    onFailure = { error ->
                        Log.e("JobViewModel", "-> Error en onFailure del repositorio: ${error.message}")
                        errorMessage = error.message
                    }
                )
            } catch (e: Exception) {
                Log.e("JobViewModel", "-> Excepción atrapada en try-catch: ${e.message}")
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
            errorMessage = null

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
                        Log.d("JobViewModel", "-> Trabajo creado con éxito: ${createdJob.titulo}")
                        onSuccess()
                    },
                    onFailure = { error ->
                        val errorBody = if (error is retrofit2.HttpException) {
                            error.response()?.errorBody()?.string() ?: error.message
                        } else {
                            error.message
                        }
                        errorMessage = errorBody
                        onError(errorBody ?: "Error al crear el trabajo")
                    }
                )
            } catch (e: Exception) {
                errorMessage = e.localizedMessage
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
            errorMessage = null

            val authHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"

            try {
                // Se envía exclusivamente el mensaje en el Body ya que el jobId viaja por la URL
                val request = CreateApplicationDto(mensaje = mensaje)
                val result = jobsRepository.applyToJob(authHeader, jobId, request)

                result.fold(
                    onSuccess = {
                        Log.d("JobViewModel", "-> Postulación enviada con éxito")
                        onSuccess()
                    },
                    onFailure = { error ->
                        val errorMsg = if (error is retrofit2.HttpException) {
                            error.response()?.errorBody()?.string() ?: error.message
                        } else {
                            error.message
                        }
                        Log.e("JobViewModel", "-> Error al postularse: $errorMsg")
                        onError(errorMsg ?: "Error al postularse")
                    }
                )
            } catch (e: Exception) {
                Log.e("JobViewModel", "-> Excepción al postularse: ${e.message}")
                onError("Error de conexión: ${e.localizedMessage}")
            } finally {
                isLoading = false
            }
        }
    }

    fun getJobById(
        token: String,
        jobId: Int,
        onSuccess: (JobResponseDto) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val authHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"

            try {
                val result = jobsRepository.getJobById(authHeader, jobId)

                result.fold(
                    onSuccess = { jobDto ->
                        onSuccess(jobDto)
                    },
                    onFailure = { error ->
                        val errorMsg = error.localizedMessage ?: "No se encontró el trabajo"
                        Log.e("JobViewModel", "-> Error al obtener trabajo: $errorMsg")
                        onError(errorMsg)
                    }
                )
            } catch (e: Exception) {
                Log.e("JobViewModel", "-> Excepción al obtener trabajo por ID: ${e.message}")
                onError("Error de red: ${e.localizedMessage}")
            } finally {
                isLoading = false
            }
        }
    }
}