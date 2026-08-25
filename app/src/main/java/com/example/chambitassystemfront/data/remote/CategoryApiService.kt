package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.CategoryDto
import com.example.chambitassystemfront.data.model.CreateCategoryRequest
import com.example.chambitassystemfront.data.model.MessageResponseDto
import com.example.chambitassystemfront.data.model.UpdateCategoryRequest
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface CategoryApiService {

    @GET("categories")
    suspend fun getCategories(): List<CategoryDto>

    @GET("categories/{id}")
    suspend fun getCategoryById(
        @Path("id") id: Int
    ): CategoryDto

    @POST("categories")
    suspend fun createCategory(
        @Body request: CreateCategoryRequest
    ): CategoryDto

    @PATCH("categories/{id}")
    suspend fun updateCategory(
        @Path("id") id: Int,
        @Body request: UpdateCategoryRequest
    ): CategoryDto

    @DELETE("categories/{id}")
    suspend fun deleteCategory(@Path("id") id: Int): MessageResponseDto
}
