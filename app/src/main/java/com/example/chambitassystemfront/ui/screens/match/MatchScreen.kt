package com.example.chambitassystemfront.ui.screens.match

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
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
    match: MatchResponseDto?, // 💡 Recibimos el match real
    onGoToChat: () -> Unit,
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
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Regresar")
            }
            Text(text = "Match", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(40.dp))

        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(RoundedCornerShape(30.dp))
                .background(Color(0xFFEAE2FF)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Match",
                tint = Color(0xFF4B20C9),
                modifier = Modifier.size(70.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = "¡Es un Match!", fontSize = 32.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "🎉 ¡Excelente!", fontSize = 18.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "La postulación fue aceptada.", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(28.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "¡Ya pueden comenzar!", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier.size(55.dp).clip(RoundedCornerShape(14.dp)).background(Color(0xFFEAE2FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Usuario", tint = Color(0xFF4B20C9), modifier = Modifier.size(32.dp))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Conectados", tint = Color(0xFF2E7D32), modifier = Modifier.size(28.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Box(
                        modifier = Modifier.size(55.dp).clip(RoundedCornerShape(14.dp)).background(Color(0xFFEAE2FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Work, contentDescription = "Trabajo", tint = Color(0xFF4B20C9), modifier = Modifier.size(32.dp))
                    }
                }

                Spacer(modifier = Modifier.height(18.dp))

                // Muestra el ID del match o trabajo real de la base de datos
                Text(
                    text = "Match #${match?.id ?: 0} (Trabajo #${match?.jobId ?: 0})",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Ahora pueden comunicarse para coordinar el trabajo.", fontSize = 14.sp, color = Color.Gray)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = onGoToChat, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
            Icon(imageVector = Icons.Default.Chat, contentDescription = "Chat")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Ir al chat", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(onClick = onGoToHome, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(12.dp)) {
            Icon(imageVector = Icons.Default.Home, contentDescription = "Inicio")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Ir al inicio", fontSize = 16.sp)
        }
    }
}