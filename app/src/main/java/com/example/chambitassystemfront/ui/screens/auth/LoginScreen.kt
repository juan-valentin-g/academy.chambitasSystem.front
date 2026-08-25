package com.example.chambitassystemfront.ui.screens.auth

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.data.model.LoginRequestDto
import com.example.chambitassystemfront.data.remote.ApiClient
import com.example.chambitassystemfront.data.repository.AuthRepository
import com.example.chambitassystemfront.data.session.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onAdminLogin: () -> Unit,
    onRegisterClick: () -> Unit,
    onBackClick: () -> Unit,
    onForgotPasswordClick: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var generalError by remember { mutableStateOf("") }

    val coroutineScope = rememberCoroutineScope()

    val authRepository = remember {
        try {
            AuthRepository(ApiClient.authApiService)
        } catch (e: Exception) {
            Log.e("LoginScreen", "Error al inicializar AuthRepository: ${e.message}", e)
            null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "🔐 Iniciar sesión", fontSize = 30.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Ingresa a tu cuenta de Chambitas", fontSize = 16.sp)
        Spacer(modifier = Modifier.height(24.dp))

        if (generalError.isNotEmpty()) {
            Text(
                text = generalError,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it; generalError = "" },
            label = { Text("Correo electrónico") },
            placeholder = { Text("ejemplo@correo.com") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it; generalError = "" },
            label = { Text("Contraseña") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onForgotPasswordClick) {
            Text("¿Olvidaste tu contraseña?")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    generalError = "Por favor completa todos los campos."
                    return@Button
                }

                if (authRepository == null) {
                    generalError = "Error interno: Repositorio no disponible."
                    return@Button
                }

                coroutineScope.launch(Dispatchers.IO) {
                    try {
                        val result = authRepository.login(
                            LoginRequestDto(email = email.trim(), password = password)
                        )

                        result.fold(
                            onSuccess = { response ->
                                val userId = response.user?.id ?: 0
                                val userRole = response.user?.rol

                                SessionManager.saveSession(
                                    accessToken = response.accessToken,
                                    userId = userId,
                                    userRole = userRole
                                )

                                withContext(Dispatchers.Main) {
                                    if (userRole == "admin") {
                                        onAdminLogin()
                                    } else {
                                        onLoginSuccess()
                                    }
                                }
                            },
                            onFailure = {
                                withContext(Dispatchers.Main) {
                                    generalError = "Correo o contraseña incorrectos."
                                }
                            }
                        )
                    } catch (e: Exception) {
                        withContext(Dispatchers.Main) {
                            generalError = "Error de conexión con el servidor."
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("🔑 Iniciar sesión")
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onRegisterClick, modifier = Modifier.fillMaxWidth()) {
            Text("👤 ¿No tienes cuenta? Regístrate")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onBackClick, modifier = Modifier.fillMaxWidth()) {
            Text("← Regresar")
        }
    }
}
