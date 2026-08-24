package com.example.chambitassystemfront.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.data.model.RegisterRequestDto
import com.example.chambitassystemfront.data.remote.ApiClient
import com.example.chambitassystemfront.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit,
    onBackClick: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var nameError by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var phoneError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var confirmPasswordError by remember { mutableStateOf("") }
    var generalError by remember { mutableStateOf("") } // Para mostrar el error exacto de la API

    val coroutineScope = rememberCoroutineScope()
    val authRepository = remember { AuthRepository(ApiClient.authApiService) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "👤 Crear cuenta",
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Regístrate para comenzar a usar Chambitas",
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mensaje detallado si ocurre un fallo al conectar con la API
        if (generalError.isNotEmpty()) {
            Text(
                text = generalError,
                color = Color.Red,
                fontSize = 13.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // =========================
        // NOMBRE
        // =========================

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = ""
                generalError = ""
            },
            label = {
                Text("Nombre completo")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Nombre"
                )
            },
            singleLine = true,
            isError = nameError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        if (nameError.isNotEmpty()) {
            Text(
                text = nameError,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // =========================
        // CORREO
        // =========================

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = ""
                generalError = ""
            },
            label = {
                Text("Correo electrónico")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "Correo"
                )
            },
            singleLine = true,
            isError = emailError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        if (emailError.isNotEmpty()) {
            Text(
                text = emailError,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // =========================
        // TELÉFONO
        // =========================

        OutlinedTextField(
            value = phone,
            onValueChange = {
                if (it.all { character -> character.isDigit() } && it.length <= 10) {
                    phone = it
                    phoneError = ""
                    generalError = ""
                }
            },
            label = {
                Text("Teléfono")
            },
            placeholder = {
                Text("10 dígitos")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Teléfono"
                )
            },
            singleLine = true,
            isError = phoneError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        if (phoneError.isNotEmpty()) {
            Text(
                text = phoneError,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // =========================
        // CONTRASEÑA
        // =========================

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = ""
                generalError = ""
            },
            label = {
                Text("Contraseña")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Contraseña"
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            isError = passwordError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        if (passwordError.isNotEmpty()) {
            Text(
                text = passwordError,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // =========================
        // CONFIRMAR CONTRASEÑA
        // =========================

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = ""
                generalError = ""
            },
            label = {
                Text("Confirmar contraseña")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Confirmar contraseña"
                )
            },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            isError = confirmPasswordError.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )

        if (confirmPasswordError.isNotEmpty()) {
            Text(
                text = confirmPasswordError,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // =========================
        // BOTÓN REGISTRAR
        // =========================

        Button(
            onClick = {

                // Limpiar errores anteriores
                nameError = ""
                emailError = ""
                phoneError = ""
                passwordError = ""
                confirmPasswordError = ""
                generalError = ""

                var isValid = true

                // Validaciones locales
                when {
                    name.isBlank() -> { nameError = "Ingresa tu nombre completo."; isValid = false }
                    name.trim().length < 3 -> { nameError = "El nombre debe tener al menos 3 caracteres."; isValid = false }
                }

                val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")
                when {
                    email.isBlank() -> { emailError = "Ingresa tu correo electrónico."; isValid = false }
                    !email.matches(emailRegex) -> { emailError = "Ingresa un correo electrónico válido."; isValid = false }
                }

                when {
                    phone.isBlank() -> { phoneError = "Ingresa tu número de teléfono."; isValid = false }
                    phone.length != 10 -> { phoneError = "El teléfono debe tener exactamente 10 dígitos."; isValid = false }
                }

                when {
                    password.isBlank() -> { passwordError = "Ingresa una contraseña."; isValid = false }
                    password.length < 6 -> { passwordError = "La contraseña debe tener al menos 6 caracteres."; isValid = false }
                }

                when {
                    confirmPassword.isBlank() -> { confirmPasswordError = "Confirma tu contraseña."; isValid = false }
                    password != confirmPassword -> { confirmPasswordError = "Las contraseñas no coinciden."; isValid = false }
                }

                // Si pasa las validaciones locales, enviamos los datos a la API
                if (isValid) {
                    coroutineScope.launch(Dispatchers.IO) {
                        try {
                            val result = authRepository.register(
                                RegisterRequestDto(
                                    nombre = name.trim(),
                                    correo = email.trim(),
                                    telefono = phone.trim(),
                                    contrasena = password
                                )
                            )

                            result.fold(
                                onSuccess = {
                                    withContext(Dispatchers.Main) {
                                        onRegisterSuccess()
                                    }
                                },
                                onFailure = { error ->
                                    error.printStackTrace()
                                    val errorMessage = error.localizedMessage ?: "Error desconocido"
                                    withContext(Dispatchers.Main) {
                                        generalError = "Fallo de API: $errorMessage"
                                    }
                                }
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                            val catchMessage = e.localizedMessage ?: "Excepción de red"
                            withContext(Dispatchers.Main) {
                                generalError = "Excepción: $catchMessage"
                            }
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "✓ Registrarme"
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        TextButton(
            onClick = onBackClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("← Regresar")
        }
    }
}