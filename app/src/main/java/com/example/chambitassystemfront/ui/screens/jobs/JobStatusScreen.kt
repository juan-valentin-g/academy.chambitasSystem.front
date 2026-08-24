package com.example.chambitassystemfront.ui.screens.jobs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.data.model.JobResponseDto

@Composable
fun JobStatusScreen(
    job: JobResponseDto?, // Recibe el objeto real del trabajo
    onCompleteJob: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF9F7FF))
    ) {
        // Barra Superior
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(start = 8.dp, end = 20.dp, top = 12.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar")
            }
            Spacer(modifier = Modifier.width(4.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = "Trabajo en proceso", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Text(text = "Gestiona el estado de tu chambita", fontSize = 12.sp, color = Color.Gray)
            }
            Icon(
                imageVector = Icons.Default.Work,
                contentDescription = "Trabajo",
                tint = Color(0xFF4B20C9),
                modifier = Modifier.size(30.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFEAE2FF))
            ) {
                Row(
                    modifier = Modifier.padding(18.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "En proceso",
                        tint = Color(0xFF4B20C9),
                        modifier = Modifier.size(42.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(text = "Trabajo en proceso", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(text = "La chambita está actualmente activa.", fontSize = 13.sp, color = Color.DarkGray)
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Información del trabajo", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(12.dp))

            // Tarjeta con la información dinámica de la base de datos
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Work, contentDescription = "Trabajo", tint = Color(0xFF4B20C9))
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = job?.titulo ?: "Cargando título...",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(14.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Propietario", tint = Color(0xFF4B20C9))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "ID del Trabajo", fontSize = 12.sp, color = Color.Gray)
                            // Corregido para usar el ID del trabajo de forma segura
                            Text(text = "#${job?.id ?: 0}", fontWeight = FontWeight.Medium)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Ubicación", tint = Color(0xFF4B20C9))
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Ubicación", fontSize = 12.sp, color = Color.Gray)
                            Text(text = job?.ubicacion ?: "No especificada", fontWeight = FontWeight.Medium)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "💰", fontSize = 20.sp)
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(text = "Presupuesto", fontSize = 12.sp, color = Color.Gray)
                            Text(
                                text = "$${job?.presupuesto ?: 0.0}",
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(text = "Progreso", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Trabajo iniciado", fontSize = 13.sp)
                        Text(text = "En proceso", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4B20C9))
                        Text(text = "Completado", fontSize = 13.sp, color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    LinearProgressIndicator(
                        progress = { 0.6f },
                        modifier = Modifier.fillMaxWidth().height(8.dp),
                        color = Color(0xFF4B20C9),
                        trackColor = Color(0xFFE5E0F0)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = onCompleteJob,
                modifier = Modifier.fillMaxWidth().height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4B20C9))
            ) {
                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Completar")
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Marcar como completado", fontSize = 16.sp)
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Cuando el trabajo termine, podrás calificar la experiencia.",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}