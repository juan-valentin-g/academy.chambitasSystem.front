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

        Spacer(modifier = Modifier.height(20.dp))

        // =========================
        // NOMBRE
        // =========================

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameError = ""
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
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // =========================
        // CORREO
        // =========================

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = ""
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
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // =========================
        // TELÉFONO
        // =========================

        OutlinedTextField(
            value = phone,
            onValueChange = {
                if (it.all { character -> character.isDigit() } && it.length <= 10) {
                    phone = it
                    phoneError = ""
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
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // =========================
        // CONTRASEÑA
        // =========================

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = ""
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
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // =========================
        // CONFIRMAR CONTRASEÑA
        // =========================

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = ""
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
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

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

                var isValid = true

                // -------------------------
                // VALIDAR NOMBRE
                // -------------------------

                when {
                    name.isBlank() -> {
                        nameError = "Ingresa tu nombre completo."
                        isValid = false
                    }

                    name.trim().length < 3 -> {
                        nameError = "El nombre debe tener al menos 3 caracteres."
                        isValid = false
                    }

                    !name.trim().matches(
                        Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+$")
                    ) -> {
                        nameError = "El nombre solo puede contener letras."
                        isValid = false
                    }
                }

                // -------------------------
                // VALIDAR CORREO
                // -------------------------

                val emailRegex = Regex(
                    "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"
                )

                when {
                    email.isBlank() -> {
                        emailError = "Ingresa tu correo electrónico."
                        isValid = false
                    }

                    email.contains(" ") -> {
                        emailError = "El correo no puede contener espacios."
                        isValid = false
                    }

                    !email.matches(emailRegex) -> {
                        emailError = "Ingresa un correo electrónico válido."
                        isValid = false
                    }
                }

                // -------------------------
                // VALIDAR TELÉFONO
                // -------------------------

                when {
                    phone.isBlank() -> {
                        phoneError = "Ingresa tu número de teléfono."
                        isValid = false
                    }

                    phone.length != 10 -> {
                        phoneError = "El teléfono debe tener exactamente 10 dígitos."
                        isValid = false
                    }
                }

                // -------------------------
                // VALIDAR CONTRASEÑA
                // -------------------------

                when {
                    password.isBlank() -> {
                        passwordError = "Ingresa una contraseña."
                        isValid = false
                    }

                    password.length < 8 -> {
                        passwordError =
                            "La contraseña debe tener al menos 8 caracteres."
                        isValid = false
                    }

                    password.contains(" ") -> {
                        passwordError =
                            "La contraseña no puede contener espacios."
                        isValid = false
                    }

                    !password.any { it.isUpperCase() } -> {
                        passwordError =
                            "Debe contener al menos una letra mayúscula."
                        isValid = false
                    }

                    !password.any { it.isLowerCase() } -> {
                        passwordError =
                            "Debe contener al menos una letra minúscula."
                        isValid = false
                    }

                    !password.any { it.isDigit() } -> {
                        passwordError =
                            "Debe contener al menos un número."
                        isValid = false
                    }
                }

                // -------------------------
                // CONFIRMAR CONTRASEÑA
                // -------------------------

                when {
                    confirmPassword.isBlank() -> {
                        confirmPasswordError =
                            "Confirma tu contraseña."
                        isValid = false
                    }

                    password != confirmPassword -> {
                        confirmPasswordError =
                            "Las contraseñas no coinciden."
                        isValid = false
                    }
                }

                // -------------------------
                // REGISTRO CORRECTO
                // -------------------------

                if (isValid) {
                    onRegisterSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "✓ Registrarme"
            )
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