package com.example.chambitassystemfront.ui.screens.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.data.session.SessionManager

@Composable
fun AdminUsersScreen(
    viewModel: AdminUsersViewModel,
    onBackClick: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.fetchUsers()
    }

    Column(modifier = Modifier.fillMaxSize().padding(20.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBackClick) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Regresar"
                )
            }
            Spacer(modifier = Modifier.width(6.dp))
            Icon(Icons.Default.People, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Usuarios", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        }

        Text(
            text = "Activa o desactiva el acceso de las cuentas registradas.",
            color = Color.Gray
        )

        viewModel.errorMessage?.let {
            Spacer(modifier = Modifier.height(10.dp))
            Text(it, color = Color(0xFFD32F2F))
        }

        Spacer(modifier = Modifier.height(16.dp))

        when {
            viewModel.isLoading && viewModel.users.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            viewModel.users.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay usuarios registrados")
                }
            }

            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(viewModel.users, key = { it.id }) { user ->
                        val isCurrentUser = user.id == SessionManager.userId
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(user.nombre, fontWeight = FontWeight.Bold)
                                    Text(user.email, color = Color.Gray, fontSize = 13.sp)
                                    Text(
                                        text = buildString {
                                            append(user.rol)
                                            append(" · ")
                                            append(if (user.activo) "Activo" else "Inactivo")
                                            if (isCurrentUser) append(" · Tu cuenta")
                                        },
                                        color = if (user.activo) {
                                            Color(0xFF2E7D32)
                                        } else {
                                            Color(0xFFD32F2F)
                                        },
                                        fontSize = 12.sp
                                    )
                                }

                                if (viewModel.updatingUserId == user.id) {
                                    CircularProgressIndicator()
                                } else {
                                    Switch(
                                        checked = user.activo,
                                        enabled = !isCurrentUser,
                                        onCheckedChange = {
                                            viewModel.updateStatus(user, it)
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

