package com.example.chambitassystemfront.ui.screens.jobs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchJobsScreen(
    onJobClick: (Int) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Buscar trabajos",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = "",
            onValueChange = {},
            label = { Text("¿Qué chambita buscas?") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        JobCard(
            title = "Limpieza de casa",
            price = "$200",
            location = "Ciudad de México",
            onClick = {
                onJobClick(1)
            }
        )

        Spacer(modifier = Modifier.height(12.dp))

        JobCard(
            title = "Ayuda para mudanza",
            price = "$350",
            location = "Ciudad de México",
            onClick = {
                onJobClick(2)
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Regresar")
        }
    }
}

@Composable
private fun JobCard(
    title: String,
    price: String,
    location: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 19.sp
            )

            Text(text = price)

            Text(text = location)

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = onClick) {
                Text("Ver detalle")
            }
        }
    }
}