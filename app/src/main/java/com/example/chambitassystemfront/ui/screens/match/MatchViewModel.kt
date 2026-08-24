package com.example.chambitassystemfront.ui.screens.match

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chambitassystemfront.data.model.MatchResponseDto
import com.example.chambitassystemfront.data.repository.MatchesRepository
import kotlinx.coroutines.launch

class MatchViewModel(private val matchesRepository: MatchesRepository) : ViewModel() {

    var matches by mutableStateOf<List<MatchResponseDto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchMatches(token: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            val authHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"

            val result = matchesRepository.getMatches(authHeader)
            result.fold(
                onSuccess = { list ->
                    matches = list
                },
                onFailure = { error ->
                    errorMessage = error.message
                }
            )
            isLoading = false
        }
    }
}

class MatchViewModelFactory(private val repository: MatchesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MatchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}