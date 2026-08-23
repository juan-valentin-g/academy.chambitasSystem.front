package com.example.chambitassystemfront.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@Composable
fun ProfileScreen(
    onBackClick: () -> Unit,
    onApplicationsClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
    ){

        // =========================================================
        // ENCABEZADO
        // =========================================================

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    start = 8.dp,
                    end = 20.dp,
                    top = 12.dp,
                    bottom = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBackClick
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar"
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "Mi perfil",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Perfil",
                tint = Color(0xFF4B20C9),
                modifier = Modifier.size(30.dp)
            )
        }

        // =========================================================
        // CONTENIDO
        // =========================================================

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {

            // =====================================================
            // TARJETA DEL PERFIL
            // =====================================================

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(22.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    // Foto de perfil
                    Box(
                        modifier = Modifier
                            .size(90.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFEAE2FF)),
                        contentAlignment = Alignment.Center
                    ) {

                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Foto de perfil",
                            tint = Color(0xFF4B20C9),
                            modifier = Modifier.size(55.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Nombre
                    Text(
                        text = "Usuario de Chambitas",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    // Estado
                    Text(
                        text = "Disponible para chambitas",
                        fontSize = 13.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Calificación
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Calificación",
                            tint = Color(0xFFFFB300),
                            modifier = Modifier.size(20.dp)
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "4.8",
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = " · 24 trabajos realizados",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    // =================================================
                    // BOTÓN EDITAR PERFIL
                    // =================================================

                    OutlinedButton(
                        onClick = onEditProfileClick,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(14.dp)
                    ) {

                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar perfil"
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text("Editar perfil")
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // =====================================================
            // ESTADÍSTICAS
            // =====================================================

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                ProfileStatCard(
                    icon = Icons.Default.Work,
                    value = "24",
                    label = "Trabajos",
                    modifier = Modifier.weight(1f)
                )

                ProfileStatCard(
                    icon = Icons.Default.Star,
                    value = "4.8",
                    label = "Calificación",
                    modifier = Modifier.weight(1f)
                )

                ProfileStatCard(
                    icon = Icons.Default.Favorite,
                    value = "98%",
                    label = "Respuesta",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(22.dp))

            // =====================================================
            // MI ACTIVIDAD
            // =====================================================

            Text(
                text = "Mi actividad",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Mis postulaciones
            ProfileOption(
                icon = Icons.Default.List,
                title = "Mis postulaciones",
                description = "Consulta tus trabajos y postulaciones",
                onClick = onApplicationsClick
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Mis trabajos
            ProfileOption(
                icon = Icons.Default.Work,
                title = "Mis trabajos",
                description = "Consulta los trabajos que has realizado",
                onClick = {
                    // Pendiente de conectar con su pantalla
                }
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Configuración
            ProfileOption(
                icon = Icons.Default.Settings,
                title = "Configuración",
                description = "Administra las opciones de tu cuenta",
                onClick = {
                    // Pendiente de conectar con su pantalla
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            // =====================================================
            // CERRAR SESIÓN
            // =====================================================

            OutlinedButton(
                onClick = onLogoutClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFFD32F2F)
                )
            ) {

                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Cerrar sesión"
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Cerrar sesión",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            // =====================================================
            // REGRESAR
            // =====================================================

            OutlinedButton(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp)
            ) {

                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Regresar"
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Regresar")
            }
        }
    }
}


// ================================================================
// TARJETA DE ESTADÍSTICA
// ================================================================

@Composable
private fun ProfileStatCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier.height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF4B20C9),
                modifier = Modifier.size(25.dp)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
            )

            Text(
                text = label,
                fontSize = 11.sp,
                color = Color.Gray
            )
        }
    }
}


// ================================================================
// OPCIÓN DEL PERFIL
// ================================================================

@Composable
private fun ProfileOption(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    description: String,
    onClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        onClick = onClick
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFEAE2FF)),
                contentAlignment = Alignment.Center
            ) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Color(0xFF4B20C9)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )

                Text(
                    text = description,
                    fontSize = 11.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
