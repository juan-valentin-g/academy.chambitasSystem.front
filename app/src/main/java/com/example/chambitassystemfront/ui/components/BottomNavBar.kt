package com.example.chambitassystemfront.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.ui.theme.PurplePrimary

sealed class BottomNavItem(val route: String, val title: String, val icon: ImageVector) {
    object Home : BottomNavItem("home", "Inicio", Icons.Outlined.Home)
    object Search : BottomNavItem("search_jobs", "Buscar", Icons.Outlined.Search)
    object Publish : BottomNavItem("publish_job", "Publicar", Icons.Filled.Add)
    object Messages : BottomNavItem("messages", "Mensajes", Icons.Outlined.ChatBubbleOutline)
    object Profile : BottomNavItem("profile", "Perfil", Icons.Outlined.PersonOutline)
}

@Composable
fun BottomNavBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Publish,
        BottomNavItem.Messages,
        BottomNavItem.Profile
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        items.forEach { item ->
            val isSelected = currentRoute == item.route

            if (item == BottomNavItem.Publish) {
                NavigationBarItem(
                    selected = false,
                    onClick = { onNavigate(item.route) },
                    icon = {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .offset(y = (-4).dp)
                                .clip(CircleShape)
                                .background(PurplePrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                tint = Color.White,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    },
                    label = null
                )
            } else {
                NavigationBarItem(
                    selected = isSelected,
                    onClick = { onNavigate(item.route) },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            tint = if (isSelected) PurplePrimary else MaterialTheme.colorScheme.outline
                        )
                    },
                    label = {
                        Text(
                            text = item.title,
                            fontSize = 11.sp,
                            color = if (isSelected) PurplePrimary else MaterialTheme.colorScheme.outline
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        indicatorColor = PurplePrimary.copy(alpha = 0.12f)
                    )
                )
            }
        }
    }
}