package com.example.chambitassystemfront.data.remote

import com.example.chambitassystemfront.data.model.CategoryDto
import com.example.chambitassystemfront.data.model.CreateCategoryRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface CategoryApiService {

    @GET("categories")
    suspend fun getCategories(
        @Header("Authorization") token: String
    ): Response<List<CategoryDto>>

    @POST("categories")
    suspend fun createCategory(
        @Header("Authorization") token: String,
        @Body request: CreateCategoryRequest
    ): Response<CategoryDto>
}