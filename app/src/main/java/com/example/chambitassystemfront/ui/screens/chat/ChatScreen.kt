package com.example.chambitassystemfront.ui.screens.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.ui.screens.jobs.JobViewModel

data class ChatMessage(
    val text: String,
    val isMine: Boolean
)

@Composable
fun ChatScreen(
    token: String,
    jobId: Int,
    jobViewModel: JobViewModel,
    onBackClick: () -> Unit
) {
    var message by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    // 🚀 Cargamos el mensaje inicial de la postulación o dejamos la conversación abierta
    var messages by remember {
        mutableStateOf(
            listOf(
                ChatMessage(
                    text = "¡Hola! Mi postulación para este trabajo ha sido aceptada. ¡Comenzamos!",
                    isMine = false
                )
            )
        )
    }

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
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar"
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Surface(
                modifier = Modifier.size(45.dp),
                shape = RoundedCornerShape(14.dp),
                color = Color(0xFFEAE2FF)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Usuario",
                        tint = Color(0xFF4B20C9),
                        modifier = Modifier.size(27.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Chat de Match",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Trabajo ID: #$jobId", // 👈 Muestra el ID real recibido del match
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            Icon(
                imageVector = Icons.Default.Work,
                contentDescription = "Trabajo",
                tint = Color(0xFF4B20C9)
            )
        }

        // Lista de mensajes
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages) { chatMessage ->
                MessageBubble(
                    message = chatMessage.text,
                    isMine = chatMessage.isMine
                )
            }
        }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color(0xFFD32F2F),
                fontSize = 13.sp,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }

        // Barra inferior de escritura
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = Color.White,
            shadowElevation = 4.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = message,
                    onValueChange = {
                        message = it
                        errorMessage = ""
                    },
                    placeholder = { Text("Escribe un mensaje...") },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.width(8.dp))

                FloatingActionButton(
                    onClick = {
                        if (message.isNotBlank()) {
                            val textoAEnviar = message

                            // Añadimos visualmente el mensaje de forma instantánea al chat
                            messages = messages + ChatMessage(
                                text = textoAEnviar,
                                isMine = true
                            )
                            message = ""

                            // 🚀 Aquí puedes conectar el envío de mensajes o llamadas al ViewModel si lo requieres
                            if (jobId > 0) {
                                jobViewModel.applyToJob(
                                    token = token,
                                    jobId = jobId,
                                    mensaje = textoAEnviar,
                                    onSuccess = {
                                        // Mensaje enviado / postulación actualizada
                                    },
                                    onError = { errorMsg: String ->
                                        errorMessage = errorMsg
                                    }
                                )
                            }
                        }
                    },
                    containerColor = Color(0xFF4B20C9),
                    contentColor = Color.White,
                    modifier = Modifier.size(52.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Enviar"
                    )
                }
            }
        }
    }
}

@Composable
private fun MessageBubble(
    message: String,
    isMine: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            color = if (isMine) Color(0xFF4B20C9) else Color.White,
            shape = if (isMine) {
                RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 18.dp, bottomEnd = 4.dp)
            } else {
                RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 4.dp, bottomEnd = 18.dp)
            },
            shadowElevation = 2.dp
        ) {
            Text(
                text = message,
                color = if (isMine) Color.White else Color.DarkGray,
                modifier = Modifier
                    .widthIn(max = 280.dp)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                fontSize = 15.sp
            )
        }
    }
}