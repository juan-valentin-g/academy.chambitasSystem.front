    package com.example.chambitassystemfront.ui.screens.auth

    import android.content.Context
    import android.util.Log // 👈 Importante para ver los logs en la consola
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.Button
    import androidx.compose.material3.OutlinedTextField
    import androidx.compose.material3.Text
    import androidx.compose.material3.TextButton
    import androidx.compose.runtime.*
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import com.example.chambitassystemfront.data.model.LoginRequestDto
    import com.example.chambitassystemfront.data.remote.ApiClient
    import com.example.chambitassystemfront.data.repository.AuthRepository
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

        val context = LocalContext.current
        val coroutineScope = rememberCoroutineScope()

        // Verificamos que authApiService no sea nulo de forma segura
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
            Text(
                text = "🔐 Iniciar sesión",
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Ingresa a tu cuenta de Chambitas",
                fontSize = 16.sp
            )

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
                onValueChange = {
                    email = it
                    generalError = ""
                },
                label = { Text("Correo electrónico") },
                placeholder = { Text("ejemplo@correo.com") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    generalError = ""
                },
                label = { Text("Contraseña") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onForgotPasswordClick
            ) {
                Text("¿Olvidaste tu contraseña?")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // BOTÓN INICIO DE SESIÓN CON API
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

                    Log.d("LoginScreen", "Intentando iniciar sesión con: ${email.trim()}")

                    coroutineScope.launch(Dispatchers.IO) {
                        try {
                            val result = authRepository.login(
                                LoginRequestDto(email = email.trim(), password = password)
                            )

                            result.fold(
                                onSuccess = { response ->
                                    Log.d("LoginScreen", "Login exitoso. Token recibido.")
                                    ApiClient.userToken = response.accessToken

                                    // Guardar de forma persistente en SharedPreferences
                                    val sharedPreferences = context.getSharedPreferences("ChambitasPrefs", Context.MODE_PRIVATE)
                                    sharedPreferences.edit().putString("USER_TOKEN", response.accessToken).apply()

                                    withContext(Dispatchers.Main) {
                                        val cleanEmail = email.trim().lowercase()

                                        if (cleanEmail.contains("admin") || cleanEmail == "admin@chambitas.com") {
                                            onAdminLogin()
                                        } else {
                                            onLoginSuccess()
                                        }
                                    }
                                },
                                onFailure = { error ->
                                    Log.e("LoginScreen", "Fallo en la autenticación: ${error.message}", error)
                                    withContext(Dispatchers.Main) {
                                        generalError = "Correo o contraseña incorrectos."
                                    }
                                }
                            )
                        } catch (e: Exception) {
                            Log.e("LoginScreen", "Excepción crítica durante el login: ${e.localizedMessage}", e)
                            withContext(Dispatchers.Main) {
                                generalError = "Error de conexión con el servidor: ${e.localizedMessage ?: "Desconocido"}"
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

            TextButton(
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("👤 ¿No tienes cuenta? Regístrate")
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onBackClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("← Regresar")
            }
        }
    }