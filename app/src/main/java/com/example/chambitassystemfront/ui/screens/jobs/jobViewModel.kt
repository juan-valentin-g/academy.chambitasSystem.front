package com.example.chambitassystemfront.ui.screens.jobs

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chambitassystemfront.data.model.CreateJobDto
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

            // 🔍 LOG DE DIAGNÓSTICO PARA VERIFICAR EL TOKEN
            Log.d("DEBUG_AUTH", "Token recibido: '$token' | Header final enviado: '$authHeader'")

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
                        // 🔍 Capturar el cuerpo de error exacto que envía NestJS
                        val errorBody = if (error is retrofit2.HttpException) {
                            error.response()?.errorBody()?.string() ?: error.message
                        } else {
                            error.message
                        }

                        Log.e("ERROR_BACKEND_REAL", "-> Código 401 Detalle del Servidor: $errorBody")
                        errorMessage = errorBody
                        onError(errorBody ?: "Error al crear el trabajo")
                    }
                )
            } catch (e: Exception) {
                Log.e("JobViewModel", "-> Excepción al crear trabajo: ${e.message}")
                errorMessage = e.localizedMessage
                onError(e.localizedMessage ?: "Error inesperado")
            } finally {
                isLoading = false
            }
        }
    }
}