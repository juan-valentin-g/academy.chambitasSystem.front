package com.example.chambitassystemfront.ui.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdminDashboardScreen(
    onUsersClick: () -> Unit,
    onCategoriesClick: () -> Unit,
    onBackClick: () -> Unit,
    onLogoutClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.AdminPanelSettings,
                contentDescription = "Administrador",
                modifier = Modifier.size(40.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Panel de administración",
                    fontSize = 26.sp
                )

                Text(
                    text = "Gestiona Chambitas",
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        Text(
            text = "Administración",
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.People,
                    contentDescription = "Usuarios",
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Gestionar usuarios",
                        fontSize = 18.sp
                    )

                    Text(
                        text = "Consulta y administra usuarios"
                    )
                }

                Button(
                    onClick = onUsersClick
                ) {
                    Text("Ver")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Category,
                    contentDescription = "Categorías",
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Gestionar categorías",
                        fontSize = 18.sp
                    )

                    Text(
                        text = "Administra las categorías de trabajos"
                    )
                }

                Button(
                    onClick = onCategoriesClick
                ) {
                    Text("Ver")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Flag,
                    contentDescription = "Reportes",
                    modifier = Modifier.size(40.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {

                    Text(
                        text = "Gestionar reportes",
                        fontSize = 18.sp
                    )

                    Text(
                        text = "Revisa reportes de usuarios"
                    )
                }

                Text("Sin endpoint", color = MaterialTheme.colorScheme.outline)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = onLogoutClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFD32F2F))
        ) {
            Icon(imageVector = Icons.Default.Logout, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Cerrar sesión", fontWeight = FontWeight.Bold)
        }
    }
}
