package com.example.chambitassystemfront.ui.screens.applications

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chambitassystemfront.data.model.ApplicationResponseDto
import com.example.chambitassystemfront.data.model.MatchResponseDto
import com.example.chambitassystemfront.data.repository.ApplicationsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.launch

class ApplicationsViewModel(
    private val repository: ApplicationsRepository
) : ViewModel() {

    var sentApplications by mutableStateOf<List<ApplicationResponseDto>>(emptyList())
        private set

    var receivedApplications by mutableStateOf<List<ApplicationResponseDto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchSentApplications(
        onLoaded: (List<ApplicationResponseDto>) -> Unit = {}
    ) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.getMyApplications().fold(
                onSuccess = {
                    sentApplications = it
                    onLoaded(it)
                },
                onFailure = { errorMessage = it.message }
            )
            isLoading = false
        }
    }

    fun fetchApplicationsByJob(jobId: Int) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.getApplicationsByJob(jobId).fold(
                onSuccess = { applications ->
                    receivedApplications =
                        receivedApplications.filterNot { it.jobId == jobId } + applications
                },
                onFailure = { errorMessage = it.message }
            )
            isLoading = false
        }
    }

    fun fetchApplicationsForMyJobs(jobIds: List<Int>) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            if (jobIds.isEmpty()) {
                receivedApplications = emptyList()
                isLoading = false
                return@launch
            }

            val results = jobIds.distinct().map { jobId ->
                async { repository.getApplicationsByJob(jobId) }
            }.awaitAll()

            val failures = results.mapNotNull { it.exceptionOrNull()?.message }
            receivedApplications = results.flatMap { it.getOrDefault(emptyList()) }
                .sortedByDescending { it.createdAt }

            if (failures.isNotEmpty()) {
                errorMessage = failures.first()
            }
            isLoading = false
        }
    }

    fun acceptApplication(
        application: ApplicationResponseDto,
        onSuccess: (MatchResponseDto) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.acceptApplication(application.id).fold(
                onSuccess = { match ->
                    receivedApplications = receivedApplications.map {
                        when {
                            it.id == application.id -> it.copy(estado = "ACEPTADA")
                            it.jobId == application.jobId &&
                                it.estado.equals("PENDIENTE", ignoreCase = true) ->
                                it.copy(estado = "RECHAZADA")
                            else -> it
                        }
                    }
                    onSuccess(match)
                },
                onFailure = {
                    val message = it.message ?: "No se pudo aceptar la postulación"
                    errorMessage = message
                    onError(message)
                }
            )
            isLoading = false
        }
    }

    fun rejectApplication(
        application: ApplicationResponseDto,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.rejectApplication(application.id).fold(
                onSuccess = { updated ->
                    receivedApplications = receivedApplications.map {
                        if (it.id == updated.id) updated else it
                    }
                    onSuccess()
                },
                onFailure = {
                    val message = it.message ?: "No se pudo rechazar la postulación"
                    errorMessage = message
                    onError(message)
                }
            )
            isLoading = false
        }
    }
}

class ApplicationsViewModelFactory(
    private val repository: ApplicationsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ApplicationsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ApplicationsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
