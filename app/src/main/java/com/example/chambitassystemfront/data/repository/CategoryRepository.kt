package com.example.chambitassystemfront.data.repository

import com.example.chambitassystemfront.data.model.CategoryDto
import com.example.chambitassystemfront.data.model.CreateCategoryRequest
import com.example.chambitassystemfront.data.model.MessageResponseDto
import com.example.chambitassystemfront.data.model.UpdateCategoryRequest
import com.example.chambitassystemfront.data.remote.CategoryApiService
import com.example.chambitassystemfront.data.remote.apiCall

class CategoryRepository(private val apiService: CategoryApiService) {

    suspend fun getCategories(): Result<List<CategoryDto>> = apiCall {
        apiService.getCategories()
    }

    suspend fun getCategoryById(id: Int): Result<CategoryDto> = apiCall {
        apiService.getCategoryById(id)
    }

    suspend fun createCategory(request: CreateCategoryRequest): Result<CategoryDto> = apiCall {
        apiService.createCategory(request)
    }

    suspend fun updateCategory(id: Int, request: UpdateCategoryRequest): Result<CategoryDto> = apiCall {
        apiService.updateCategory(id, request)
    }

    suspend fun deleteCategory(id: Int): Result<MessageResponseDto> = apiCall {
        apiService.deleteCategory(id)
    }
}
