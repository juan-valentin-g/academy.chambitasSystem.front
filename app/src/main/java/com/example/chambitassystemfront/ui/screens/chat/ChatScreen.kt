package com.example.chambitassystemfront.ui.screens.chat

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatScreen(
    onBackClick: () -> Unit
) {
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(
            text = "Chat",
            fontSize = 26.sp,
            modifier = Modifier.padding(20.dp)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            item {
                Card {
                    Text(
                        text = "¡Hola! Gracias por aceptar la chambita.",
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(10.dp))
            }

            item {
                Card {
                    Text(
                        text = "¡Hola! Con gusto. Nos vemos mañana.",
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                label = { Text("Mensaje") },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = {
                    message = ""
                }
            ) {
                Text("Enviar")
            }
        }

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text("Regresar")
        }
    }
}