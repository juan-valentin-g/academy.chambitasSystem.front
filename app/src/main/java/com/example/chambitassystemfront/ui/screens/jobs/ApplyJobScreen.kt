package com.example.chambitassystemfront.ui.screens.jobs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ApplyJobScreen(
    jobId: Int,
    onApplySuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Postularme",
            fontSize = 28.sp
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text("Cuéntale al empleador por qué eres la persona indicada.")

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            label = { Text("Mensaje") },
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = onApplySuccess,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Enviar postulación")
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cancelar")
        }
    }
}