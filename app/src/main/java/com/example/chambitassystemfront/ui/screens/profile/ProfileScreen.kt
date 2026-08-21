package com.example.chambitassystemfront.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    onApplicationsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Mi perfil",
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
                    text = "Usuario de Chambitas",
                    fontSize = 22.sp
                )

                Text("⭐ 4.8")

                Spacer(modifier = Modifier.height(12.dp))

                Text("24 trabajos")
                Text("98% de respuesta")

            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onApplicationsClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mis postulaciones")
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Editar perfil")
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