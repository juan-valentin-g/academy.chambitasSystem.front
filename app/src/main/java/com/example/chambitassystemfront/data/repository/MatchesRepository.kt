package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.MatchResponseDto
import com.example.chambitassystemfront.data.remote.ApiClient
import com.example.chambitassystemfront.data.remote.apiCall

class MatchesRepository {
    suspend fun getMatches(): Result<List<MatchResponseDto>> = apiCall {
        ApiClient.matchesApiService.getMatches()
    }

    suspend fun getMatchById(matchId: Int): Result<MatchResponseDto> = apiCall {
        ApiClient.matchesApiService.getMatchById(matchId)
    }

    suspend fun completeMatch(matchId: Int): Result<MatchResponseDto> = apiCall {
        ApiClient.matchesApiService.completeMatch(matchId)
    }
}
