package com.example.chambitassystemfront.ui.screens.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.data.model.JobResponseDto

@Composable
fun JobDetailScreen(
    token: String,
    jobId: Int,
    jobViewModel: JobViewModel,
    onApplyClick: () -> Unit,
    onBackClick: () -> Unit
) {
    var jobDetail by remember { mutableStateOf<JobResponseDto?>(null) }
    var errorMessage by remember { mutableStateOf("") }

    // Al iniciar la pantalla, cargamos el detalle del trabajo por su ID
    LaunchedEffect(jobId) {
        jobViewModel.getJobById(
            token = token,
            jobId = jobId,
            onSuccess = { job ->
                jobDetail = job
            },
            onError = { error ->
                errorMessage = error
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7FF))
            .padding(20.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar"
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Detalle del trabajo",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        if (jobViewModel.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = Color(0xFF4B20C9))
            }
        } else if (errorMessage.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = errorMessage, color = Color(0xFFD32F2F), fontSize = 16.sp)
            }
        } else {
            val job = jobDetail

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(65.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFFEAE2FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Work,
                            contentDescription = "Trabajo",
                            tint = Color(0xFF4B20C9),
                            modifier = Modifier.size(36.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = job?.titulo ?: "Cargando...",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    JobInfoRow(
                        icon = Icons.Default.AttachMoney,
                        text = "Presupuesto: $${job?.presupuesto ?: 0.0}"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    JobInfoRow(
                        icon = Icons.Default.LocationOn,
                        text = job?.ubicacion ?: "Ubicación no especificada"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    JobInfoRow(
                        icon = Icons.Default.CalendarToday,
                        text = "Fecha: ${job?.createdAt?.take(10) ?: "Por definir"}"
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    JobInfoRow(
                        icon = Icons.Default.Person,
                        text = "ID del Propietario: #${job?.ownerId ?: 0}"
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Descripción",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = job?.descripcion ?: "Sin descripción disponible.",
                        fontSize = 15.sp,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = onApplyClick,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B20C9))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Work,
                            contentDescription = "Postularme",
                            tint = Color.White
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Postularme",
                            fontSize = 16.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun JobInfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color(0xFF4B20C9),
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = text,
            fontSize = 15.sp
        )
    }
}