package com.example.chambitassystemfront.ui.screens.reviews

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.chambitassystemfront.data.model.CreateReviewDto
import com.example.chambitassystemfront.data.model.ReviewResponseDto
import com.example.chambitassystemfront.data.repository.ReviewsRepository
import kotlinx.coroutines.launch

class ReviewViewModel(
    private val repository: ReviewsRepository
) : ViewModel() {

    var matchReviews by mutableStateOf<List<ReviewResponseDto>>(emptyList())
        private set

    var receivedReviews by mutableStateOf<List<ReviewResponseDto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchMatchReviews(matchId: Int) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.getReviewsByMatch(matchId).fold(
                onSuccess = { matchReviews = it },
                onFailure = { errorMessage = it.message }
            )
            isLoading = false
        }
    }

    fun fetchReceivedReviews() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.getReceivedReviews().fold(
                onSuccess = { receivedReviews = it },
                onFailure = { errorMessage = it.message }
            )
            isLoading = false
        }
    }

    fun createReview(
        matchId: Int,
        rating: Int,
        comment: String,
        onSuccess: (ReviewResponseDto) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            repository.createReview(
                matchId,
                CreateReviewDto(
                    calificacion = rating,
                    comentario = comment.trim()
                )
            ).fold(
                onSuccess = { review ->
                    matchReviews = listOf(review) + matchReviews
                    onSuccess(review)
                },
                onFailure = {
                    val message = it.message ?: "No se pudo publicar la reseña"
                    errorMessage = message
                    onError(message)
                }
            )
            isLoading = false
        }
    }
}

class ReviewViewModelFactory(
    private val repository: ReviewsRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReviewViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReviewViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

