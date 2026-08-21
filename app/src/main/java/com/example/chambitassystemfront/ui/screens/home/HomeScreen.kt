package com.example.chambitassystemfront.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(
    onSearchClick: () -> Unit,
    onPublishClick: () -> Unit,
    onProfileClick: () -> Unit,
    onChatClick: () -> Unit
) {
    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = {
                        Icon(Icons.Default.Search, null)
                    },
                    label = { Text("Buscar") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onPublishClick,
                    icon = {
                        Icon(Icons.Default.Add, null)
                    },
                    label = { Text("Publicar") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onChatClick,
                    icon = {
                        Icon(Icons.Default.Chat, null)
                    },
                    label = { Text("Mensajes") }
                )

                NavigationBarItem(
                    selected = false,
                    onClick = onProfileClick,
                    icon = {
                        Icon(Icons.Default.Person, null)
                    },
                    label = { Text("Perfil") }
                )
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp)
        ) {

            Text(
                text = "¡Hola! 👋",
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Encuentra tu próxima chambita"
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = onSearchClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Search, null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Buscar trabajos")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Categorías",
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(
                    onClick = onSearchClick,
                    label = { Text("Limpieza") }
                )

                AssistChip(
                    onClick = onSearchClick,
                    label = { Text("Mudanzas") }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Trabajos destacados",
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Limpieza de casa",
                        fontSize = 18.sp
                    )

                    Text("$100 - $200")

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = onSearchClick
                    ) {
                        Text("Ver trabajos")
                    }
                }
            }
        }
    }
}