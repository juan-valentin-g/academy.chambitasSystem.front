package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.MatchResponseDto
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface MatchesApiService {

    @GET("matches")
    suspend fun getMatches(): List<MatchResponseDto>

    @GET("matches/{id}")
    suspend fun getMatchById(
        @Path("id") matchId: Int
    ): MatchResponseDto

    @PATCH("matches/{id}/complete")
    suspend fun completeMatch(
        @Path("id") matchId: Int
    ): MatchResponseDto
}
