package com.example.chambitassystemfront.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AccountTypeScreen(
    onContinue: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Default.Work,
            contentDescription = "Chambitas",
            modifier = Modifier.size(70.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "¡Crea tu cuenta!",
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "En Chambitas puedes publicar trabajos y también postularte a ellos.",
            fontSize = 17.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "✓ Buscar y postularte a trabajos",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "✓ Publicar tus propias chambitas",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "✓ Gestionar tus postulaciones",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "✓ Calificar y recibir calificaciones",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(30.dp))

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Continuar"
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text("Continuar con el registro")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("← Regresar")
        }
    }
}