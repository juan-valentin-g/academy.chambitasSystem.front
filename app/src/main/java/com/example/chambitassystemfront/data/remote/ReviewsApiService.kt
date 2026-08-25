package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.CreateReviewDto
import com.example.chambitassystemfront.data.model.ReviewResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewsApiService {
    @POST("matches/{matchId}/reviews")
    suspend fun createReview(
        @Path("matchId") matchId: Int,
        @Body request: CreateReviewDto
    ): ReviewResponseDto

    @GET("matches/{matchId}/reviews")
    suspend fun getReviewsByMatch(
        @Path("matchId") matchId: Int
    ): List<ReviewResponseDto>

    @GET("reviews/received")
    suspend fun getReceivedReviews(): List<ReviewResponseDto>
}

