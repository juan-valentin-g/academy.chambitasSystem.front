package com.example.chambitassystemfront.ui.screens.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.ui.screens.home.CategoryViewModel

@Composable
fun PostaJobScreen(
    jobViewModel: JobViewModel,
    categoryViewModel: CategoryViewModel,
    onBackClick: () -> Unit,
    onPublishSuccess: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var categoryId by remember { mutableStateOf<Int?>(null) }

    var errorMessage by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        categoryViewModel.fetchCategories()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7FF))
            .verticalScroll(scrollState)
    ) {
        // Barra Superior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar"
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Publicar trabajo",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Publica una nueva chambita",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.Default.Work,
                contentDescription = "Trabajo",
                tint = Color(0xFF4B20C9),
                modifier = Modifier.size(28.dp)
            )
        }

        // Formulario
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Text(
                text = "Información del trabajo",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it; errorMessage = "" },
                label = { Text("Título del trabajo") },
                placeholder = { Text("Ej. Limpieza de casa") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it; errorMessage = "" },
                label = { Text("Descripción") },
                placeholder = { Text("Describe lo que necesitas") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Categoría",
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (categoryViewModel.isLoading && categoryViewModel.categories.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else if (categoryViewModel.categories.isEmpty()) {
                Text(
                    text = "No hay categorías disponibles.",
                    color = Color(0xFFD32F2F)
                )
            } else {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(categoryViewModel.categories, key = { it.id }) { category ->
                        FilterChip(
                            selected = categoryId == category.id,
                            onClick = {
                                categoryId = category.id
                                errorMessage = ""
                            },
                            label = { Text(category.nombre) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = price,
                onValueChange = { price = it; errorMessage = "" },
                label = { Text("Presupuesto") },
                placeholder = { Text("Ej. 500") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { location = it; errorMessage = "" },
                label = { Text("Ubicación") },
                placeholder = { Text("Ej. Ciudad de México") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = errorMessage,
                    color = Color(0xFFD32F2F),
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val priceValue = price.toDoubleOrNull()

                    when {
                        title.isBlank() || description.isBlank() || price.isBlank() || location.isBlank() -> {
                            errorMessage = "Completa todos los campos antes de publicar."
                        }
                        categoryId == null -> {
                            errorMessage = "Selecciona una categoría."
                        }
                        priceValue == null -> {
                            errorMessage = "El presupuesto debe ser un número válido."
                        }
                        priceValue <= 0 -> {
                            errorMessage = "El presupuesto debe ser mayor al 0."
                        }
                        else -> {
                            errorMessage = ""
                            // Llamamos a la función real del ViewModel que envía los datos al backend
                            jobViewModel.createJob(
                                categoryId = categoryId!!,
                                titulo = title,
                                descripcion = description,
                                presupuesto = priceValue,
                                ubicacion = location,
                                onSuccess = {
                                    onPublishSuccess()
                                },
                                onError = { errorMsg ->
                                    errorMessage = errorMsg
                                }
                            )
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B20C9))
            ) {
                if (jobViewModel.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text(
                        text = "Publicar trabajo",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}
