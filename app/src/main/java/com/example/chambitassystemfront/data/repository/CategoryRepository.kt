package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.CategoryDto
import com.example.chambitassystemfront.data.model.CreateCategoryRequest
import com.example.chambitassystemfront.data.remote.CategoryApiService

class CategoryRepository(private val apiService: CategoryApiService) {

    suspend fun getCategories(token: String): Result<List<CategoryDto>> {
        return try {
            val response = apiService.getCategories("Bearer $token")
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al obtener categorías: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createCategory(token: String, request: CreateCategoryRequest): Result<CategoryDto> {
        return try {
            val response = apiService.createCategory("Bearer $token", request)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error al crear categoría: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}