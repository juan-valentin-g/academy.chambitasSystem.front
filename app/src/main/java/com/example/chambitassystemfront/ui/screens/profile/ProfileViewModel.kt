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

    fun fetchProfile() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            val result = userRepository.getProfile()
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

    fun updateProfile(name: String, phone: String, description: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            isLoading = true
            val result = userRepository.updateProfile(name, phone, description)
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
