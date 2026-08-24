package com.example.chambitassystemfront.ui.screens.applications

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Send
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chambitassystemfront.data.repository.ApplicationsRepository

@Composable
fun ApplicationsScreen(
    token: String,
    onMatchClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val viewModel: ApplicationsViewModel = viewModel(
        factory = ApplicationsViewModelFactory(ApplicationsRepository())
    )

    LaunchedEffect(Unit) {
        viewModel.fetchApplications(token)
    }

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
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar")
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "Match y Solicitudes",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Pestañas rediseñadas con el nuevo enfoque lógico
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent
        ) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text("Voy a realizar") },
                icon = { Icon(imageVector = Icons.Default.Send, contentDescription = null) }
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

                if (viewModel.sentApplications.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("No tienes postulaciones activas", color = Color.Gray)
                    }
                } else {
                    viewModel.sentApplications.forEach { app ->
                        ApplicationCard(
                            title = "Trabajo #${app.jobId}",
                            subtitle = "Estado: ${app.estado}",
                            price = "Ver detalles",
                            status = app.estado,
                            statusColor = if (app.estado == "ACEPTADA") Color(0xFF2E7D32) else Color(0xFFFFA000),
                            showActions = false,
                            onAccept = {},
                            onReject = {},
                            onMatchClick = onMatchClick
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            } else {
                Text(text = "Interesados en mis publicaciones", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(12.dp))

                if (viewModel.receivedApplications.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Nadie se ha postulado a tus ofertas aún", color = Color.Gray)
                    }
                } else {
                    viewModel.receivedApplications.forEach { app ->
                        ApplicationCard(
                            title = "Trabajo #${app.jobId}",
                            subtitle = "Postulante ID: ${app.workerId}",
                            price = "Gestionar oferta",
                            status = app.estado,
                            statusColor = when (app.estado) {
                                "ACEPTADA" -> Color(0xFF2E7D32)
                                "RECHAZADA" -> Color(0xFFD32F2F)
                                else -> Color(0xFF4B20C9)
                            },
                            showActions = app.estado == "PENDIENTE",
                            onAccept = {
                                viewModel.updateStatus(token, app.id, "ACEPTADA") {
                                    onMatchClick()
                                }
                            },
                            onReject = {
                                viewModel.updateStatus(token, app.id, "RECHAZADA") {}
                            },
                            onMatchClick = onMatchClick
                        )
                        Spacer(modifier = Modifier.height(12.dp))
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
    price: String,
    status: String,
    statusColor: Color,
    showActions: Boolean,
    onAccept: () -> Unit,
    onReject: () -> Unit,
    onMatchClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
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
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(text = price, fontSize = 13.sp, color = Color(0xFF4B20C9), fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = if (status == "ACEPTADA") Icons.Default.CheckCircle else Icons.Default.Work,
                    contentDescription = null,
                    tint = statusColor,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = "Estado: $status", color = statusColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
            }

            if (showActions) {
                Spacer(modifier = Modifier.height(14.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = onAccept,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Aceptar")
                    }

                    OutlinedButton(
                        onClick = onReject,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFD32F2F))
                    ) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Rechazar")
                    }
                }
            } else if (status == "ACEPTADA") {
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onMatchClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B20C9))
                ) {
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ir al Chat / Match")
                }
            }
        }
    }
}