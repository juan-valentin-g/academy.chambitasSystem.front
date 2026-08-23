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

@Composable
fun ApplicationsScreen(
    onMatchClick: () -> Unit,
    onBackClick: () -> Unit
) {

    var selectedTab by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7FF))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 12.dp,
                    end = 20.dp,
                    top = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar"
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Postulaciones",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = Color.Transparent
        ) {

            Tab(
                selected = selectedTab == 0,
                onClick = {
                    selectedTab = 0
                },
                text = {
                    Text("Enviadas")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null
                    )
                }
            )

            Tab(
                selected = selectedTab == 1,
                onClick = {
                    selectedTab = 1
                },
                text = {
                    Text("Recibidas")
                },
                icon = {
                    Icon(
                        imageVector = Icons.Default.Inbox,
                        contentDescription = null
                    )
                }
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            if (selectedTab == 0) {

                Text(
                    text = "Mis postulaciones",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                ApplicationCard(
                    title = "Limpieza de casa",
                    subtitle = "Ciudad de México",
                    price = "$200",
                    status = "Pendiente",
                    statusColor = Color(0xFFFFA000),
                    showActions = false,
                    onAccept = {},
                    onReject = {},
                    onMatchClick = onMatchClick
                )

                Spacer(modifier = Modifier.height(12.dp))

                ApplicationCard(
                    title = "Ayuda para mudanza",
                    subtitle = "Ciudad de México",
                    price = "$350",
                    status = "Aceptada",
                    statusColor = Color(0xFF2E7D32),
                    showActions = false,
                    onAccept = {},
                    onReject = {},
                    onMatchClick = onMatchClick
                )

            } else {

                Text(
                    text = "Postulaciones recibidas",
                    fontSize = 21.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                ApplicationCard(
                    title = "Limpieza de casa",
                    subtitle = "Postulante: María López",
                    price = "$200",
                    status = "Nueva postulación",
                    statusColor = Color(0xFF4B20C9),
                    showActions = true,
                    onAccept = onMatchClick,
                    onReject = {},
                    onMatchClick = onMatchClick
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar"
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Regresar")
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
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {

            Row(
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

                Spacer(modifier = Modifier.width(14.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = subtitle,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(3.dp))

                    Text(
                        text = "💰 $price",
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = if (
                        status == "Aceptada"
                    ) {
                        Icons.Default.CheckCircle
                    } else {
                        Icons.Default.Work
                    },
                    contentDescription = null,
                    tint = statusColor,
                    modifier = Modifier.size(20.dp)
                )

                Spacer(modifier = Modifier.width(7.dp))

                Text(
                    text = status,
                    color = statusColor,
                    fontWeight = FontWeight.Bold
                )
            }

            if (showActions) {

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Button(
                        onClick = onAccept,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Aceptar"
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text("Aceptar")
                    }

                    OutlinedButton(
                        onClick = onReject,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(10.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Rechazar"
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text("Rechazar")
                    }
                }

            } else if (status == "Aceptada") {

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = onMatchClick,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {

                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Match"
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text("Ver Match")
                }
            }
        }
    }
}