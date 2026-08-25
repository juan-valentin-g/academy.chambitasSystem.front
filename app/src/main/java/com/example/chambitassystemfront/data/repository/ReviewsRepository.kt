package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.CreateReviewDto
import com.example.chambitassystemfront.data.model.ReviewResponseDto
import com.example.chambitassystemfront.data.remote.ApiClient
import com.example.chambitassystemfront.data.remote.apiCall

class ReviewsRepository {
    suspend fun createReview(
        matchId: Int,
        request: CreateReviewDto
    ): Result<ReviewResponseDto> = apiCall {
        ApiClient.reviewsApiService.createReview(matchId, request)
    }

    suspend fun getReviewsByMatch(
        matchId: Int
    ): Result<List<ReviewResponseDto>> = apiCall {
        ApiClient.reviewsApiService.getReviewsByMatch(matchId)
    }

    suspend fun getReceivedReviews(): Result<List<ReviewResponseDto>> = apiCall {
        ApiClient.reviewsApiService.getReceivedReviews()
    }
}

