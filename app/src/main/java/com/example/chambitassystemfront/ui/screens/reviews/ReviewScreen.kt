package com.example.chambitassystemfront.ui.screens.reviews

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReviewScreen(
    onSubmitReview: () -> Unit,
    onBackClick: () -> Unit
) {
    var rating by remember { mutableIntStateOf(5) }
    var comment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Reseña y calificación",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("¿Cómo fue tu experiencia?")

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "⭐".repeat(rating),
            fontSize = 30.sp
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            for (i in 1..5) {
                TextButton(
                    onClick = { rating = i }
                ) {
                    Text("$i")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Escribe una reseña") },
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onSubmitReview,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar reseña")
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Regresar")
        }
    }
}