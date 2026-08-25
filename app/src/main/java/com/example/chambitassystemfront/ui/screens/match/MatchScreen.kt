package com.example.chambitassystemfront.ui.screens.match

import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.data.model.MatchResponseDto

@Composable
fun MatchScreen(
    match: MatchResponseDto?,
    availableMatches: List<MatchResponseDto> = emptyList(),
    isLoading: Boolean,
    errorMessage: String?,
    onSelectMatch: (Int) -> Unit = {},
    onOpenStatus: (Int) -> Unit,
    onGoToHome: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7FF))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar"
                )
            }
            Text(text = "Match", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        if (availableMatches.size > 1) {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(availableMatches, key = { it.id }) { item ->
                    FilterChip(
                        selected = item.id == match?.id,
                        onClick = { onSelectMatch(item.id) },
                        label = { Text(item.job?.titulo ?: "Match #${item.id}") }
                    )
                }
            }
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
                        text = errorMessage ?: "No tienes matches disponibles",
                        color = if (errorMessage == null) Color.Gray else Color(0xFFD32F2F)
                    )
                }
            }

            else -> {
                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(RoundedCornerShape(28.dp))
                        .background(Color(0xFFEAE2FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0xFF4B20C9),
                        modifier = Modifier.size(64.dp)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = if (match.estado == "ACTIVO") "¡Es un match!" else "Match finalizado",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = match.job?.titulo ?: "Trabajo #${match.jobId}",
                    color = Color.Gray,
                    fontSize = 16.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Participant(
                                name = match.employer?.nombre ?: "Empleador #${match.employerId}",
                                isWorker = false
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = Color(0xFF2E7D32)
                            )
                            Spacer(modifier = Modifier.width(14.dp))
                            Participant(
                                name = match.worker?.nombre ?: "Trabajador #${match.workerId}",
                                isWorker = true
                            )
                        }

                        Spacer(modifier = Modifier.height(18.dp))
                        Text(
                            text = "Estado: ${match.estado}",
                            fontWeight = FontWeight.Bold,
                            color = if (match.estado == "ACTIVO") {
                                Color(0xFF2E7D32)
                            } else {
                                Color.Gray
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                Button(
                    onClick = { onOpenStatus(match.id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Work, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        if (match.estado == "ACTIVO") {
                            "Gestionar trabajo"
                        } else {
                            "Ver trabajo completado"
                        }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Card(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Chat, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Chat pendiente de contrato de API", color = Color.Gray)
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedButton(
                    onClick = onGoToHome,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Home, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ir al inicio")
                }
            }
        }
    }
}

@Composable
private fun Participant(name: String, isWorker: Boolean) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = if (isWorker) Icons.Default.Person else Icons.Default.Work,
            contentDescription = null,
            tint = Color(0xFF4B20C9),
            modifier = Modifier.size(34.dp)
        )
        Text(name, fontSize = 11.sp)
    }
}
