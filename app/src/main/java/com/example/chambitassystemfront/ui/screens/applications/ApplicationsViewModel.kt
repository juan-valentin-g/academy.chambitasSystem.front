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

    fun fetchApplications(token: String) {
        viewModelScope.launch {
            isLoading = true
            val authHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"

            repository.getSentApplications(authHeader).onSuccess {
                sentApplications = it
            }
            repository.getReceivedApplications(authHeader).onSuccess {
                receivedApplications = it
            }

            isLoading = false
        }
    }

    fun updateStatus(token: String, appId: Int, newStatus: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            val authHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"
            repository.updateStatus(authHeader, appId, newStatus).onSuccess {
                fetchApplications(authHeader)
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