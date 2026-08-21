package com.example.chambitassystemfront.ui.screens.match

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MatchScreen(
    onGoToChat: () -> Unit,
    onGoToHome: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "🎉",
            fontSize = 60.sp
        )

        Text(
            text = "¡Es un Match!",
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "La postulación fue aceptada."
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onGoToChat,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir al chat")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onGoToHome,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir al inicio")
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(
            onClick = onBackClick
        ) {
            Text("Regresar")
        }
    }
}