package com.example.chambitassystemfront.ui.screens.jobs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
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

data class JobItem(
    val id: Int,
    val title: String,
    val price: String,
    val location: String,
    val timeAgo: String,
    val iconRes: Int,
    val badgeColor: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchJobsScreen(
    onJobClick: (Int) -> Unit,
    onBackClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val jobs = remember {
        listOf(
            JobItem(
                id = 1,
                title = "Limpieza de departamento",
                price = "$100",
                location = "Centro, CDMX",
                timeAgo = "Publicado hace 2 horas",
                iconRes = R.drawable.ic_limpieza,
                badgeColor = Color(0xFFF0ECFB)
            ),
            JobItem(
                id = 2,
                title = "Pintura de fachada",
                price = "$300 - $500",
                location = "Narvarte, CDMX",
                timeAgo = "Publicado hace 4 horas",
                iconRes = R.drawable.ic_mudanza,
                badgeColor = Color(0xFFF0ECFB)
            ),
            JobItem(
                id = 3,
                title = "Cuido de mascotas",
                price = "$100 - $150",
                location = "Coyoacán, CDMX",
                timeAgo = "Publicado hace 6 horas",
                iconRes = R.drawable.ic_mascotas,
                badgeColor = Color(0xFFEAF5EA)
            ),
            JobItem(
                id = 4,
                title = "Mantenimiento de jardín",
                price = "$200 - $350",
                location = "Del Valle, CDMX",
                timeAgo = "Publicado hace 1 día",
                iconRes = R.drawable.ic_jardineria,
                badgeColor = Color(0xFFEAF5EA)
            ),
            JobItem(
                id = 5,
                title = "Servicios generales / Otros",
                price = "$150 - $250",
                location = "Polanco, CDMX",
                timeAgo = "Publicado hace 2 días",
                iconRes = R.drawable.ic_otros,
                badgeColor = Color(0xFFF1F3F5)
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
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Buscar trabajos",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Regresar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                ),
                windowInsets = WindowInsets(0.dp) // Elimina el margen superior del status bar si aplica
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
            contentPadding = PaddingValues(top = 4.dp, bottom = 24.dp) // Pegado más hacia arriba
        ) {
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = {
                        Text(
                            "Buscar trabajos, categorías...",
                            fontSize = 13.sp,
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
                    trailingIcon = {
                        Surface(
                            onClick = { /* TODO: Abrir Filtros */ },
                            shape = RoundedCornerShape(20.dp),
                            color = PurplePrimary.copy(alpha = 0.12f),
                            modifier = Modifier.padding(end = 6.dp)
                        ) {
                            Text(
                                text = "Filtros",
                                color = PurplePrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
                        focusedBorderColor = PurplePrimary
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }

            items(filteredJobs, key = { it.id }) { job ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onJobClick(job.id) },
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(job.badgeColor),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = job.iconRes),
                                contentDescription = job.title,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = job.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = job.price,
                                color = Color(0xFFB87333),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = job.location,
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = job.timeAgo,
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.7f)
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