package com.example.chambitassystemfront.ui.screens.jobs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.R
import com.example.chambitassystemfront.ui.theme.ChambitasSystemFrontTheme
import com.example.chambitassystemfront.ui.theme.PurplePrimary

data class JobCategory(
    val name: String,
    val iconRes: Int, // Referencia al drawable R.drawable.ic_...
    val badgeColor: Color
)

data class JobItem(
    val id: Int,
    val title: String,
    val price: String,
    val location: String,
    val iconRes: Int, // Referencia al drawable R.drawable.ic_...
    val badgeColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchJobsScreen(onJobClick: (Int) -> Unit) {
    var searchQuery by remember { mutableStateOf("") }

    // Reemplaza R.drawable.ic_limpieza, etc., por los nombres exactos de tus archivos en res/drawable
    val categories = remember {
        listOf(
            JobCategory("Limpieza", R.drawable.ic_limpieza, Color(0xFFF0ECFB)),
            JobCategory("Mudanzas", R.drawable.ic_mudanza, Color(0xFFFFF4E5)),
            JobCategory("Jardinería", R.drawable.ic_jardineria, Color(0xFFEAF5EA)),
            JobCategory("mascotas", R.drawable.ic_mascotas, Color(0xFFEAF5EA)),
            JobCategory("Otros", R.drawable.ic_otros, Color(0xFFF1F3F5))
        )
    }

    val jobs = remember {
        listOf(
            JobItem(
                1,
                "Limpieza de casa",
                "$80 - $120",
                "Centro, CDMX",
                R.drawable.ic_limpieza,
                Color(0xFFF0ECFB)
            ),
            JobItem(
                2,
                "Ayuda para mudanza",
                "$150 - $200",
                "Del Valle, CDMX",
                R.drawable.ic_mudanza,
                Color(0xFFFFF4E5)
            ),
            JobItem(
                3,
                "Cuidado de mascotas",
                "$100 - $150",
                "Narvarte, CDMX",
                R.drawable.ic_mascotas,
                Color(0xFFEAF5EA)
            )
        )
    }

    val filteredJobs = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            jobs
        } else {
            jobs.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.location.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Buscar trabajos",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Buscador
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            "Buscar trabajos, categorías...",
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.outline
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Buscar",
                            tint = MaterialTheme.colorScheme.outline
                        )
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // Sección Categorías
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Categorías",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                    TextButton(onClick = { }) {
                        Text(
                            text = "Ver todas",
                            color = PurplePrimary,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }
                }

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(categories) { category ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.width(72.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(category.badgeColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = category.iconRes),
                                    contentDescription = category.name,
                                    modifier = Modifier.size(38.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = category.name,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Sección Lista de Trabajos
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Trabajos cerca de ti",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }

            items(filteredJobs, key = { it.id }) { job ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onJobClick(job.id) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            // Badge con la ilustración del trabajo
                            Box(
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(job.badgeColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = job.iconRes),
                                    contentDescription = null,
                                    modifier = Modifier.size(34.dp)
                                )
                            }

                            Column {
                                Text(
                                    text = job.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = job.price,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp
                                )
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = job.location,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }

                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "Guardar",
                                tint = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchJobsScreenPreview() {
    ChambitasSystemFrontTheme {
        SearchJobsScreen(
            onJobClick = {}
        )
    }
}