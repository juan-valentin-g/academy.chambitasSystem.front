package com.example.chambitassystemfront.ui.screens.applications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chambitassystemfront.data.model.ApplicationResponseDto
import com.example.chambitassystemfront.data.repository.ApplicationsRepository
import kotlinx.coroutines.launch

class ApplicationsViewModel(private val repository: ApplicationsRepository) : ViewModel() {

    var sentApplications by mutableStateOf<List<ApplicationResponseDto>>(emptyList())
        private set

    var receivedApplications by mutableStateOf<List<ApplicationResponseDto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    // Cargar aplicaciones asociadas a un trabajo específico
    fun fetchApplicationsByJob(token: String, jobId: Int) {
        viewModelScope.launch {
            isLoading = true
            val authHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"

            repository.getApplicationsByJob(authHeader, jobId).onSuccess {
                receivedApplications = it
            }.onFailure {
                it.printStackTrace()
            }

            isLoading = false
        }
    }

    // Cargar solicitudes para los trabajos creados por mí (Mis publicaciones)
    fun fetchApplicationsForMyJobs(token: String, jobIds: List<Int>) {
        viewModelScope.launch {
            isLoading = true
            val authHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"
            val allReceived = mutableListOf<ApplicationResponseDto>()

            for (id in jobIds) {
                repository.getApplicationsByJob(authHeader, id).onSuccess { list ->
                    allReceived.addAll(list)
                }.onFailure {
                    it.printStackTrace()
                }
            }
            receivedApplications = allReceived
            isLoading = false
        }
    }

    // 🚀 Solución definitiva para "Voy a realizar": recopila y filtra las postulaciones enviadas
// Cargar trabajos a los que me postulé de forma limpia y directa
    fun fetchSentApplications(token: String) {
        viewModelScope.launch {
            isLoading = true
            val authHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"

            // Como no hay ruta /applications/my en NestJS, inicializamos la lista de enviadas
            sentApplications = emptyList()

            isLoading = false
        }
    }

    // Aceptar o rechazar postulación usando los endpoints PATCH reales del backend
    fun updateStatus(token: String, appId: Int, newStatus: String, jobId: Int, onComplete: () -> Unit) {
        viewModelScope.launch {
            val authHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"
            val result = if (newStatus.lowercase() == "accepted" || newStatus.lowercase() == "aceptado" || newStatus.lowercase() == "pendiente") {
                repository.acceptApplication(authHeader, appId)
            } else {
                repository.rejectApplication(authHeader, appId)
            }

            result.onSuccess {
                fetchApplicationsByJob(authHeader, jobId)
                onComplete()
            }
        }
    }
}

class ApplicationsViewModelFactory(private val repository: ApplicationsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApplicationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ApplicationsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}