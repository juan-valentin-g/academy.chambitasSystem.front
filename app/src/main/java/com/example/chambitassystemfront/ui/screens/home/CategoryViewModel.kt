package com.example.chambitassystemfront.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chambitassystemfront.data.model.CategoryDto
import com.example.chambitassystemfront.data.model.CreateCategoryRequest
import com.example.chambitassystemfront.data.model.UpdateCategoryRequest
import com.example.chambitassystemfront.data.remote.ApiClient
import com.example.chambitassystemfront.data.repository.CategoryRepository
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val repository: CategoryRepository = CategoryRepository(ApiClient.categoryApiService)
) : ViewModel() {

    var categories by mutableStateOf<List<CategoryDto>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun fetchCategories() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            repository.getCategories().fold(
                onSuccess = { categories = it },
                onFailure = { errorMessage = it.message }
            )
            isLoading = false
        }
    }

    fun createCategory(
        nombre: String,
        descripcion: String,
        icono: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            repository.createCategory(
                CreateCategoryRequest(nombre.trim(), descripcion.trim(), icono?.trim()?.ifEmpty { null })
            ).fold(
                onSuccess = { created ->
                    categories = categories + created
                    onSuccess()
                },
                onFailure = { onError(it.message ?: "No se pudo crear la categoria") }
            )
            isLoading = false
        }
    }

    fun updateCategory(
        id: Int,
        nombre: String,
        descripcion: String,
        icono: String?,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            repository.updateCategory(
                id,
                UpdateCategoryRequest(nombre.trim(), descripcion.trim(), icono?.trim()?.ifEmpty { null })
            ).fold(
                onSuccess = { updated ->
                    categories = categories.map { if (it.id == updated.id) updated else it }
                    onSuccess()
                },
                onFailure = { onError(it.message ?: "No se pudo actualizar la categoria") }
            )
            isLoading = false
        }
    }

    fun deleteCategory(
        id: Int,
        onSuccess: () -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            isLoading = true
            repository.deleteCategory(id).fold(
                onSuccess = {
                    categories = categories.filterNot { it.id == id }
                    onSuccess()
                },
                onFailure = { onError(it.message ?: "No se pudo eliminar la categoria") }
            )
            isLoading = false
        }
    }

    fun clearError() {
        errorMessage = null
    }
}
