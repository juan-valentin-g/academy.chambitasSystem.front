package com.example.chambitassystemfront.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.LockReset
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.ui.theme.ChambitasSystemFrontTheme
import com.example.chambitassystemfront.ui.theme.PurplePrimary

@Composable
fun ForgotPasswordScreen(
    onBackToLogin: () -> Unit // Este es el parámetro clave para que el NavHost o el Login lo invoquen
) {

    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    // Validación del correo
    fun validateEmail(): Boolean {

        return when {

            email.isBlank() -> {
                emailError = "El correo electrónico es obligatorio."
                false
            }

            !android.util.Patterns.EMAIL_ADDRESS
                .matcher(email.trim())
                .matches() -> {

                emailError = "Ingresa un correo electrónico válido."
                false
            }

            else -> {
                emailError = ""
                true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // Icono principal
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    PurplePrimary.copy(alpha = 0.1f)
                ),
            contentAlignment = Alignment.Center
        ) {

            Icon(
                imageVector = Icons.Filled.LockReset,
                contentDescription = "Recuperar contraseña",
                tint = PurplePrimary,
                modifier = Modifier.size(44.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¿Olvidaste tu contraseña?",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Ingresa tu correo registrado para enviarte las instrucciones de recuperación.",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 20.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo de correo
        OutlinedTextField(
            value = email,

            onValueChange = {
                email = it

                // Limpiar errores al escribir nuevamente
                emailError = ""

                // Si modifica el correo después de enviarlo,
                // quitamos el mensaje de éxito.
                isSubmitted = false
            },

            placeholder = {
                Text("Correo electrónico")
            },

            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Correo",
                    tint = PurplePrimary
                )
            },

            singleLine = true,

            modifier = Modifier.fillMaxWidth(),

            shape = RoundedCornerShape(14.dp),

            isError = emailError.isNotEmpty(),

            supportingText = {

                if (emailError.isNotEmpty()) {

                    Text(
                        text = emailError,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Botón enviar
        Button(
            onClick = {

                if (validateEmail()) {
                    isSubmitted = true
                } else {
                    isSubmitted = false
                }
            },

            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),

            shape = RoundedCornerShape(14.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = PurplePrimary
            )
        ) {

            Text(
                text = "Enviar correo",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Mensaje de éxito
        if (isSubmitted) {

            Spacer(modifier = Modifier.height(16.dp))

            Surface(
                color = Color(0xFFE8F5E9),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Text(
                    text = "✓ Se ha enviado un enlace a tu correo.",
                    color = Color(0xFF2E7D32),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        TextButton(
            onClick = onBackToLogin
        ) {

            Text(
                text = "Regresar al inicio de sesión",
                color = PurplePrimary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun ForgotPasswordScreenPreview() {

    ChambitasSystemFrontTheme {

        ForgotPasswordScreen(
            onBackToLogin = {}
        )
    }
}