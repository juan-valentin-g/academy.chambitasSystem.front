package com.example.chambitassystemfront.ui.screens.jobs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun JobStatusScreen(
    onCompleteJob: () -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Trabajo en proceso",
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
                    fontSize = 20.sp
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text("Estado: En proceso")
                Text("Presupuesto: $200")

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = onCompleteJob,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Marcar como completado")
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