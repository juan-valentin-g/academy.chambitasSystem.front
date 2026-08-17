package com.example.chambitassystemfront

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chambitassystemfront.ui.theme.ChambitasSystemFrontTheme


val Purple = Color(0xFF5B2BBF)
val LightPurple = Color(0xFFF3EEFC)
val Green = Color(0xFFA8E6AF)
val Yellow = Color(0xFFFFD166)
val DarkGray = Color(0xFF333333)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChambitasSystemFrontTheme {

                ChambitasApp()

            }
        }
    }
}

@Composable
fun ChambitasApp() {

    var currentScreen by remember {
        mutableStateOf("home")
    }

    when (currentScreen) {

        "home" -> {
            HomeScreen(
                onLogin = {
                    currentScreen = "login"
                },
                onRegister = {
                    currentScreen = "register"
                }
            )
        }

        "login" -> {
            LoginScreen(
                onBack = {
                    currentScreen = "home"
                },
                onRegister = {
                    currentScreen = "register"
                }
            )
        }

        "register" -> {
            RegisterScreen(
                onBack = {
                    currentScreen = "home"
                },
                onLogin = {
                    currentScreen = "login"
                }
            )
        }
    }
}



@Composable
fun HomeScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightPurple)
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "💼",
            fontSize = 70.sp
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Text(
            text = "Chambitas",
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
            color = Purple
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Trabajos pequeños,\ngrandes oportunidades.",
            fontSize = 18.sp,
            color = DarkGray,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(45.dp)
        )

        Button(
            onClick = onLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple
            )
        ) {

            Text(
                text = "Iniciar sesión",
                fontSize = 16.sp
            )
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        OutlinedButton(
            onClick = onRegister,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(15.dp)
        ) {

            Text(
                text = "Registrarse",
                fontSize = 16.sp,
                color = Purple
            )
        }
    }
}



@Composable
fun LoginScreen(
    onBack: () -> Unit,
    onRegister: () -> Unit
) {

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(50.dp)
        )

        Text(
            text = "💼",
            fontSize = 55.sp
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Chambitas",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Purple
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Iniciar sesión",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = DarkGray
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Correo electrónico")
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Contraseña")
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        TextButton(
            onClick = {
            }
        ) {

            Text(
                text = "¿Olvidaste tu contraseña?",
                color = Purple
            )
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Button(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple
            )
        ) {

            Text(
                text = "Iniciar sesión",
                fontSize = 16.sp
            )
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "¿No tienes una cuenta?",
            color = Color.Gray
        )

        TextButton(
            onClick = onRegister
        ) {

            Text(
                text = "Regístrate",
                color = Purple,
                fontWeight = FontWeight.Bold
            )
        }

        TextButton(
            onClick = onBack
        ) {

            Text(
                text = "← Volver",
                color = Purple
            )
        }
    }
}



@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onLogin: () -> Unit
) {

    var name by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(
            modifier = Modifier.height(35.dp)
        )

        Text(
            text = "💼",
            fontSize = 50.sp
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Crear cuenta",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Purple
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Nombre completo")
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Correo electrónico")
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Contraseña")
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Confirmar contraseña")
            },
            singleLine = true
        )

        Spacer(
            modifier = Modifier.height(25.dp)
        )

        Button(
            onClick = {
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(15.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple
            )
        ) {

            Text(
                text = "Registrarme",
                fontSize = 16.sp
            )
        }

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        TextButton(
            onClick = onLogin
        ) {

            Text(
                text = "¿Ya tienes una cuenta? Inicia sesión",
                color = Purple
            )
        }

        TextButton(
            onClick = onBack
        ) {

            Text(
                text = "← Volver",
                color = Purple
            )
        }
    }
}