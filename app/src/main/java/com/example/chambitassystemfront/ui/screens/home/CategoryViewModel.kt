package com.example.chambitassystemfront.ui.screens.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chambitassystemfront.data.model.CategoryDto
import com.example.chambitassystemfront.data.remote.ApiClient
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    var categories by mutableStateOf<List<CategoryDto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchCategories(token: String) {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                // Hacemos la petición a la API enviando el token
                val response = ApiClient.categoryApiService.getCategories("Bearer $token")

                if (response.isSuccessful) {
                    categories = response.body() ?: emptyList()
                } else {
                    errorMessage = "Error al cargar categorías: ${response.code()}"
                }
            } catch (e: Exception) {
                errorMessage = e.localizedMessage ?: "Error de red desconocido"
                Log.e("CategoryViewModel", "Error fetching categories", e)
            } finally {
                isLoading = false
            }
        }
    }
}