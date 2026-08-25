package com.example.chambitassystemfront.ui.screens.applications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Inbox
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
import com.example.chambitassystemfront.ui.screens.jobs.JobViewModel

@Composable
fun ApplicationsScreen(
    applicationsViewModel: ApplicationsViewModel,
    jobViewModel: JobViewModel,
    token: String,
    onMatchClick: () -> Unit,
    onBackClick: () -> Unit,
    onJobClick: (Int) -> Unit
) {
    LaunchedEffect(Unit) {
        token.let {
            // 1. Descargamos los trabajos del servidor
            jobViewModel.fetchJobs(it)

            // ⚠️ IMPORTANTE: Asegúrate de pasar el ID real de tu usuario logueado (por ejemplo, 1 o el que uses en tu BD)
            // Si ya tienes guardado el ID en tu sesión, ponlo aquí:
            jobViewModel.setCurrentUserId(1) // Cambia 1 por el ID de tu usuario si es diferente

            // 2. Esperamos un instante o evaluamos las publicaciones propias
            val myJobIds = jobViewModel.myPublications.map { job -> job.id }
            if (myJobIds.isNotEmpty()) {
                applicationsViewModel.fetchApplicationsForMyJobs(it, myJobIds)
            } else {
                // Si por alguna razón la lista filtrada llega vacía, consultamos el trabajo 1 para probar la UI
                applicationsViewModel.fetchApplicationsByJob(it, 1)
            }

            applicationsViewModel.fetchSentApplications(it)
        }
    }

    // ... resto de tu UI ...

    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7FF))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, end = 20.dp, top = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Regresar")
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Match y Solicitudes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        PrimaryTabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Voy a realizar") },
                icon = { Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = null) }
            )

            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text("Mis publicaciones") },
                icon = { Icon(imageVector = Icons.Default.Inbox, contentDescription = null) }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            if (selectedTab == 0) {
                Text(text = "Trabajos a los que me postulé", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))

                val sentApplications = applicationsViewModel.sentApplications

                if (sentApplications.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No tienes postulaciones activas", color = Color.Gray)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(sentApplications) { app ->
                            ApplicationCard(
                                title = "Solicitud en Trabajo #${app.jobId}",
                                subtitle = "Estado de postulación",
                                status = app.estado,
                                statusColor = Color(0xFFFFA000),
                                onCardClick = { onJobClick(app.jobId) },
                                onAcceptClick = null
                            )
                        }
                    }
                }
            } else {
                Text(text = "Mis publicaciones creadas", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))

                val receivedApplications = applicationsViewModel.receivedApplications

                if (receivedApplications.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No has recibido solicitudes o publicaciones aún", color = Color.Gray)
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(receivedApplications) { app ->
                            val matchingJob = jobViewModel.myPublications.find { it.id == app.jobId }
                                ?: jobViewModel.jobs.find { it.id == app.jobId }
                            val jobTitle = matchingJob?.titulo ?: "Trabajo #${app.jobId}"

                            ApplicationCard(
                                title = jobTitle,
                                subtitle = "Postulación ID: #${app.id} (Trabajo #${app.jobId})",
                                status = app.estado,
                                statusColor = if (app.estado.uppercase() == "ACEPTADA") Color(0xFF2E7D32) else Color(0xFF4B20C9),
                                onCardClick = { onMatchClick() },
                                onAcceptClick = {
                                    applicationsViewModel.updateStatus(token, app.id, "accepted", app.jobId) {
                                        onMatchClick()
                                    }
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
private fun ApplicationCard(
    title: String,
    subtitle: String,
    status: String,
    statusColor: Color,
    onCardClick: () -> Unit,
    onAcceptClick: (() -> Unit)?
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        onClick = onCardClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFEAE2FF)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Work,
                        contentDescription = "Trabajo",
                        tint = Color(0xFF4B20C9),
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(text = title, fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = subtitle, fontSize = 13.sp, color = Color.Gray)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = statusColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Estado: $status", color = statusColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                }

                if (onAcceptClick != null && status.uppercase() != "ACEPTADA") {
                    Button(
                        onClick = onAcceptClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B20C9)),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text(text = "Aceptar", fontSize = 13.sp, color = Color.White)
                    }
                }
            }
        }
    }
}