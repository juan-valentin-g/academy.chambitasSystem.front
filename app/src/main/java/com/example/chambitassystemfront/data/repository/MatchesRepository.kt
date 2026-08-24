package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.CreateMatchDto
import com.example.chambitassystemfront.data.model.MatchResponseDto
import com.example.chambitassystemfront.data.remote.ApiClient

class MatchesRepository {
    suspend fun getMatches(token: String): Result<List<MatchResponseDto>> {
        return try {
            val response = ApiClient.matchesApiService.getMatches(token)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    suspend fun createMatch(token: String, request: CreateMatchDto): Result<MatchResponseDto> {
        return try {
            val response = ApiClient.matchesApiService.createMatch(token, request)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }
}