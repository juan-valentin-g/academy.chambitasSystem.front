package com.example.chambitassystemfront.ui.screens.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.data.model.CategoryDto
import com.example.chambitassystemfront.ui.screens.home.CategoryViewModel

@Composable
fun CategoriesScreen(
    categoryViewModel: CategoryViewModel,
    onBackClick: () -> Unit
) {
    var editingCategory by remember { mutableStateOf<CategoryDto?>(null) }
    var showForm by remember { mutableStateOf(false) }
    var categoryToDelete by remember { mutableStateOf<CategoryDto?>(null) }
    var operationError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        categoryViewModel.fetchCategories()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(text = "Categorías", fontSize = 28.sp)
        Text(text = "Administra las categorías disponibles en Chambitas")

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                editingCategory = null
                operationError = null
                showForm = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Add, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Agregar categoría")
        }

        val visibleError = operationError ?: categoryViewModel.errorMessage
        if (visibleError != null) {
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = visibleError, color = Color(0xFFD32F2F))
        }

        Spacer(modifier = Modifier.height(14.dp))

        when {
            categoryViewModel.isLoading && categoryViewModel.categories.isEmpty() -> {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            categoryViewModel.categories.isEmpty() -> {
                Box(
                    modifier = Modifier.weight(1f).fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay categorías registradas")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(categoryViewModel.categories, key = { it.id }) { category ->
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(14.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Folder,
                                    contentDescription = null,
                                    modifier = Modifier.size(34.dp)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = category.nombre, fontSize = 18.sp)
                                    Text(
                                        text = category.descripcion,
                                        fontSize = 13.sp,
                                        color = Color.Gray
                                    )
                                }
                                IconButton(onClick = {
                                    editingCategory = category
                                    operationError = null
                                    showForm = true
                                }) {
                                    Icon(Icons.Default.Edit, contentDescription = "Editar categoría")
                                }
                                IconButton(onClick = {
                                    operationError = null
                                    categoryToDelete = category
                                }) {
                                    Icon(
                                        Icons.Default.Delete,
                                        contentDescription = "Eliminar categoría",
                                        tint = Color(0xFFD32F2F)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(onClick = onBackClick, modifier = Modifier.fillMaxWidth()) {
            Text("Regresar")
        }
    }

    if (showForm) {
        CategoryFormDialog(
            category = editingCategory,
            isLoading = categoryViewModel.isLoading,
            onDismiss = { showForm = false },
            onSave = { nombre, descripcion, icono ->
                val category = editingCategory
                if (category == null) {
                    categoryViewModel.createCategory(
                        nombre = nombre,
                        descripcion = descripcion,
                        icono = icono,
                        onSuccess = { showForm = false },
                        onError = { operationError = it }
                    )
                } else {
                    categoryViewModel.updateCategory(
                        id = category.id,
                        nombre = nombre,
                        descripcion = descripcion,
                        icono = icono,
                        onSuccess = { showForm = false },
                        onError = { operationError = it }
                    )
                }
            }
        )
    }

    categoryToDelete?.let { category ->
        AlertDialog(
            onDismissRequest = { categoryToDelete = null },
            title = { Text("Eliminar categoría") },
            text = { Text("¿Deseas eliminar ${category.nombre}?") },
            confirmButton = {
                TextButton(
                    enabled = !categoryViewModel.isLoading,
                    onClick = {
                        categoryViewModel.deleteCategory(
                            id = category.id,
                            onSuccess = { categoryToDelete = null },
                            onError = {
                                operationError = it
                                categoryToDelete = null
                            }
                        )
                    }
                ) {
                    Text("Eliminar", color = Color(0xFFD32F2F))
                }
            },
            dismissButton = {
                TextButton(onClick = { categoryToDelete = null }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
@Composable
private fun CategoryFormDialog(
    category: CategoryDto?,
    isLoading: Boolean,
    onDismiss: () -> Unit,
    onSave: (String, String, String?) -> Unit
) {
    var nombre by remember(category?.id) { mutableStateOf(category?.nombre.orEmpty()) }
    var descripcion by remember(category?.id) {
        mutableStateOf(category?.descripcion.orEmpty())
    }
    var icono by remember(category?.id) { mutableStateOf(category?.icono.orEmpty()) }
    var validationError by remember(category?.id) { mutableStateOf<String?>(null) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (category == null) "Nueva categoría" else "Editar categoría") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it; validationError = null },
                    label = { Text("Nombre") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it; validationError = null },
                    label = { Text("Descripción") }
                )
                OutlinedTextField(
                    value = icono,
                    onValueChange = { icono = it },
                    label = { Text("Icono o URL (opcional)") },
                    singleLine = true
                )
                validationError?.let { Text(it, color = Color(0xFFD32F2F)) }
            }
        },
        confirmButton = {
            TextButton(
                enabled = !isLoading,
                onClick = {
                    if (nombre.isBlank() || descripcion.isBlank()) {
                        validationError = "Nombre y descripción son obligatorios"
                    } else {
                        onSave(nombre, descripcion, icono.ifBlank { null })
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
