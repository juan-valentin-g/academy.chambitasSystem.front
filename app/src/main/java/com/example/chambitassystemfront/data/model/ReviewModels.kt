package com.example.chambitassystemfront.data.model

import com.google.gson.annotations.SerializedName

data class CreateReviewDto(
    @SerializedName("calificacion") val calificacion: Int,
    @SerializedName("comentario") val comentario: String
)

data class ReviewResponseDto(
    @SerializedName("id") val id: Int,
    @SerializedName(value = "matchId", alternate = ["match_id"]) val matchId: Int,
    @SerializedName(value = "reviewerId", alternate = ["reviewer_id"]) val reviewerId: Int,
    @SerializedName(value = "revieweeId", alternate = ["reviewee_id"]) val revieweeId: Int,
    @SerializedName("calificacion") val calificacion: Int,
    @SerializedName("comentario") val comentario: String,
    @SerializedName(value = "createdAt", alternate = ["created_at"]) val createdAt: String,
    @SerializedName("match") val match: MatchResponseDto? = null,
    @SerializedName("reviewer") val reviewer: UserDto? = null,
    @SerializedName("reviewee") val reviewee: UserDto? = null
)

