package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.LoginRequestDto
import com.example.chambitassystemfront.data.model.LoginResponseDto
import com.example.chambitassystemfront.data.model.RegisterRequestDto
import com.example.chambitassystemfront.data.remote.AuthApiService

class AuthRepository(private val apiService: AuthApiService) {

    suspend fun login(credentials: LoginRequestDto): Result<LoginResponseDto> {
        return try {
            val response = apiService.login(credentials)
            Result.success(response)
        } catch (e: Exception) {
            e.printStackTrace() // 💡 Imprime el error real en el Logcat para depurar si vuelve a fallar
            Result.failure(e)
        }
    }

    suspend fun register(userData: RegisterRequestDto): Result<Unit> {
        return try {
            apiService.register(userData)
            Result.success(Unit)
        } catch (e: Exception) {
            e.printStackTrace() // 💡 Esto imprimirá la traza exacta en tu Logcat de Android Studio
            Result.failure(e)
        }
    }
}