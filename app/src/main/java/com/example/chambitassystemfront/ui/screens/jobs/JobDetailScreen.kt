package com.example.chambitassystemfront.ui.screens.jobs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun JobDetailScreen(
    jobId: Int,
    onApplyClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Detalle del trabajo",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Limpieza de departamento",
                    fontSize = 22.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text("💰 Presupuesto: $200")
                Text("📍 Ciudad de México")
                Text("📅 Fecha: Por definir")

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Descripción",
                    fontSize = 18.sp
                )

                Text(
                    "Se necesita apoyo para realizar limpieza general del departamento."
                )

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onApplyClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Postularme")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Regresar")
        }
    }
}