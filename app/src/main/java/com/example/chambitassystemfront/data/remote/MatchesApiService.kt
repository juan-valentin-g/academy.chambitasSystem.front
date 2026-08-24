package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.CreateMatchDto
import com.example.chambitassystemfront.data.model.MatchResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface MatchesApiService {

    @GET("matches")
    suspend fun getMatches(
        @Header("Authorization") token: String
    ): List<MatchResponseDto>

    @GET("matches/{id}")
    suspend fun getMatchById(
        @Header("Authorization") token: String,
        @Path("id") matchId: Int
    ): MatchResponseDto

    @POST("matches")
    suspend fun createMatch(
        @Header("Authorization") token: String,
        @Body request: CreateMatchDto
    ): MatchResponseDto
}