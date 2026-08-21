package com.example.chambitassystemfront.ui.screens.jobs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun JobCompletedScreen(
    onGoToReview: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "✓",
            fontSize = 60.sp
        )

        Text(
            text = "¡Trabajo completado!",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("El trabajo ha sido marcado como completado.")

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onGoToReview,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calificar experiencia")
        }
    }
}