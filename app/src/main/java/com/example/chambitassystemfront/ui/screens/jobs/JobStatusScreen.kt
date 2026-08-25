package com.example.chambitassystemfront.ui.screens.jobs

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.data.model.MatchResponseDto

@Composable
fun JobStatusScreen(
    match: MatchResponseDto?,
    isLoading: Boolean,
    errorMessage: String?,
    onCompleteMatch: (Int) -> Unit,
    onReviewClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7FF))
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().background(Color.White).padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar"
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text("Estado del trabajo", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Text("Match #${match?.id ?: 0}", fontSize = 12.sp, color = Color.Gray)
            }
            Icon(Icons.Default.Work, contentDescription = null, tint = Color(0xFF4B20C9))
        }

        when {
            isLoading && match == null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            match == null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = errorMessage ?: "No se encontró el match",
                        color = Color(0xFFD32F2F)
                    )
                }
            }

            else -> {
                val job = match.job
                Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = if (match.estado == "ACTIVO") {
                                Color(0xFFEAE2FF)
                            } else {
                                Color(0xFFE4F7EA)
                            }
                        )
                    ) {
                        Row(
                            modifier = Modifier.padding(18.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF4B20C9),
                                modifier = Modifier.size(42.dp)
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    if (match.estado == "ACTIVO") {
                                        "Trabajo en proceso"
                                    } else {
                                        "Trabajo completado"
                                    },
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text("Estado del match: ${match.estado}")
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Text(
                                job?.titulo ?: "Trabajo #${match.jobId}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            InfoRow(
                                Icons.Default.Person,
                                "Empleador: " +
                                    (match.employer?.nombre ?: "#${match.employerId}")
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            InfoRow(
                                Icons.Default.LocationOn,
                                job?.ubicacion ?: "Ubicación no especificada"
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Presupuesto: $${job?.presupuesto ?: 0.0}",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4B20C9)
                            )
                        }
                    }

                    errorMessage?.let {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(it, color = Color(0xFFD32F2F))
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        enabled = !isLoading,
                        onClick = {
                            if (match.estado == "ACTIVO") {
                                onCompleteMatch(match.id)
                            } else {
                                onReviewClick(match.id)
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(54.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4B20C9)
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                color = Color.White,
                                modifier = Modifier.size(24.dp)
                            )
                        } else {
                            Icon(
                                if (match.estado == "ACTIVO") {
                                    Icons.Default.CheckCircle
                                } else {
                                    Icons.Default.Star
                                },
                                contentDescription = null
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                if (match.estado == "ACTIVO") {
                                    "Marcar como completado"
                                } else {
                                    "Calificar experiencia"
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = Color(0xFF4B20C9))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text)
    }
}
