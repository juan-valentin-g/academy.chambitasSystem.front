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

class MatchViewModel(
    private val matchesRepository: MatchesRepository
) : ViewModel() {

    var matches by mutableStateOf<List<MatchResponseDto>>(emptyList())
        private set

    var selectedMatch by mutableStateOf<MatchResponseDto?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchMatches() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            matchesRepository.getMatches().fold(
                onSuccess = { list ->
                    matches = list
                    selectedMatch = list.firstOrNull()
                },
                onFailure = { errorMessage = it.message }
            )
            isLoading = false
        }
    }

    fun fetchMatch(matchId: Int) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            matchesRepository.getMatchById(matchId).fold(
                onSuccess = { selectedMatch = it },
                onFailure = { errorMessage = it.message }
            )
            isLoading = false
        }
    }

    fun completeMatch(
        matchId: Int,
        onSuccess: (MatchResponseDto) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            matchesRepository.completeMatch(matchId).fold(
                onSuccess = { completed ->
                    selectedMatch = completed
                    matches = matches.map {
                        if (it.id == completed.id) completed else it
                    }
                    onSuccess(completed)
                },
                onFailure = {
                    val message = it.message ?: "No se pudo completar el match"
                    errorMessage = message
                    onError(message)
                }
            )
            isLoading = false
        }
    }
}

class MatchViewModelFactory(
    private val repository: MatchesRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MatchViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MatchViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
