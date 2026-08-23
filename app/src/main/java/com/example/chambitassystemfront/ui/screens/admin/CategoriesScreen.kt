package com.example.chambitassystemfront.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CategoriesScreen(
    onBackClick: () -> Unit
) {

    val categories = listOf(
        "Limpieza" to Icons.Default.CleaningServices,
        "Mudanzas" to Icons.Default.DirectionsCar,
        "Jardinería" to Icons.Default.Park,
        "Cuidado de mascotas" to Icons.Default.Pets,
        "Otros" to Icons.Default.MoreHoriz
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "📂",
                fontSize = 34.sp
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {

                Text(
                    text = "Categorías",
                    fontSize = 28.sp
                )

                Text(
                    text = "Administra las categorías de Chambitas"
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Categorías disponibles",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        categories.forEach { (category, icon) ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector = icon,
                        contentDescription = category,
                        modifier = Modifier.size(35.dp)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = category,
                        fontSize = 18.sp,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {

            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Agregar categoría"
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text("Agregar categoría")
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("← Regresar")
        }
    }
}