package com.example.chambitassystemfront.ui.screens.admin

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chambitassystemfront.data.model.UserResponseDto
import com.example.chambitassystemfront.data.repository.UserRepository
import kotlinx.coroutines.launch

class AdminUsersViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var users by mutableStateOf<List<UserResponseDto>>(emptyList())
        private set

    var updatingUserId by mutableStateOf<Int?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchUsers() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.getUsers().fold(
                onSuccess = { users = it },
                onFailure = { errorMessage = it.message }
            )
            isLoading = false
        }
    }

    fun updateStatus(user: UserResponseDto, active: Boolean) {
        viewModelScope.launch {
            updatingUserId = user.id
            errorMessage = null
            repository.updateUserStatus(user.id, active).fold(
                onSuccess = { updated ->
                    users = users.map { if (it.id == updated.id) updated else it }
                },
                onFailure = { errorMessage = it.message }
            )
            updatingUserId = null
        }
    }
}

class AdminUsersViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AdminUsersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AdminUsersViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

