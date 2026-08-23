package com.example.chambitassystemfront.ui.screens.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class SearchJob(
    val id: Int,
    val title: String,
    val category: String,
    val price: String,
    val location: String,
    val description: String
)

@Composable
fun SearchJobsScreen(
    onJobClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {

    var searchText by remember {
        mutableStateOf("")
    }

    var selectedCategory by remember {
        mutableStateOf("Todas")
    }

    val jobs = remember {
        listOf(
            SearchJob(
                id = 1,
                title = "Limpieza de casa",
                category = "Limpieza",
                price = "$200",
                location = "Ciudad de México",
                description = "Se busca persona para realizar limpieza general."
            ),
            SearchJob(
                id = 2,
                title = "Ayuda para mudanza",
                category = "Mudanzas",
                price = "$350",
                location = "Ciudad de México",
                description = "Se necesita ayuda para cargar y acomodar muebles."
            ),
            SearchJob(
                id = 3,
                title = "Cortar césped",
                category = "Jardinería",
                price = "$250",
                location = "Ciudad de México",
                description = "Se necesita persona para mantenimiento de jardín."
            )
        )
    }

    val filteredJobs = jobs.filter { job ->

        val matchesSearch =
            searchText.isBlank() ||
                    job.title.contains(searchText, ignoreCase = true) ||
                    job.category.contains(searchText, ignoreCase = true) ||
                    job.location.contains(searchText, ignoreCase = true) ||
                    job.description.contains(searchText, ignoreCase = true)

        val matchesCategory =
            selectedCategory == "Todas" ||
                    job.category == selectedCategory

        matchesSearch && matchesCategory
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7FF))
            .padding(18.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar",
                    tint = Color(0xFF4B20C9)
                )
            }

            Text(
                text = "Buscar trabajos",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.FilterList,
                contentDescription = "Filtros",
                tint = Color(0xFF4B20C9)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            shape = RoundedCornerShape(15.dp),
            placeholder = {
                Text("Buscar trabajo, categoría...")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Buscar"
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF4B20C9),
                unfocusedBorderColor = Color.LightGray,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White
            )
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text(
            text = "Categorías",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            CategoryChip(
                text = "Todas",
                selected = selectedCategory == "Todas",
                onClick = {
                    selectedCategory = "Todas"
                }
            )

            CategoryChip(
                text = "Limpieza",
                selected = selectedCategory == "Limpieza",
                onClick = {
                    selectedCategory = "Limpieza"
                }
            )

            CategoryChip(
                text = "Mudanzas",
                selected = selectedCategory == "Mudanzas",
                onClick = {
                    selectedCategory = "Mudanzas"
                }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        CategoryChip(
            text = "Jardinería",
            selected = selectedCategory == "Jardinería",
            onClick = {
                selectedCategory = "Jardinería"
            }
        )

        Spacer(modifier = Modifier.height(22.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Trabajos disponibles",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "${filteredJobs.size} resultados",
                fontSize = 12.sp,
                color = Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (filteredJobs.isEmpty()) {

            Text(
                text = "No se encontraron trabajos.",
                color = Color.Gray,
                modifier = Modifier.padding(top = 20.dp)
            )

        } else {

            filteredJobs.forEach { job ->

                SearchJobCard(
                    title = job.title,
                    price = job.price,
                    location = job.location,
                    description = job.description,
                    onClick = {
                        onJobClick(job.id)
                    }
                )

                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun CategoryChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        color = if (selected) {
            Color(0xFF4B20C9)
        } else {
            Color.White
        },
        border = if (!selected) {
            androidx.compose.foundation.BorderStroke(
                1.dp,
                Color.LightGray
            )
        } else {
            null
        }
    ) {

        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 14.dp,
                vertical = 9.dp
            ),
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = if (selected) {
                Color.White
            } else {
                Color.DarkGray
            }
        )
    }
}

@Composable
private fun SearchJobCard(
    title: String,
    price: String,
    location: String,
    description: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        ),
        onClick = onClick
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(55.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFEAE2FF)),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        imageVector = Icons.Default.Work,
                        contentDescription = "Trabajo",
                        tint = Color(0xFF4B20C9),
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = title,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = price,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4B20C9)
                    )
                }

                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorito",
                    tint = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Ubicación",
                    tint = Color(0xFF4B20C9),
                    modifier = Modifier.size(18.dp)
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = location,
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = description,
                fontSize = 13.sp,
                color = Color.DarkGray
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {

                Text("Ver detalle")
            }
        }
    }
}