package com.example.chambitassystemfront.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chambitassystemfront.data.model.UserResponseDto
import com.example.chambitassystemfront.data.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    var user by mutableStateOf<UserResponseDto?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchProfile(token: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            val authHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"

            val result = userRepository.getProfile(authHeader)
            result.fold(
                onSuccess = { profile ->
                    user = profile
                },
                onFailure = { error ->
                    errorMessage = error.message
                }
            )
            isLoading = false
        }
    }

    fun updateProfile(token: String, name: String, phone: String, description: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            val authHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"

            val result = userRepository.updateProfile(authHeader, name, phone, description)
            result.fold(
                onSuccess = { updated ->
                    user = updated
                    onSuccess()
                },
                onFailure = { error ->
                    onError(error.message ?: "Error al actualizar perfil")
                }
            )
            isLoading = false
        }
    }
}

class ProfileViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}