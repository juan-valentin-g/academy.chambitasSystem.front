package com.example.chambitassystemfront

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
val LightGreen = Color(0xFFE8F8EA)
val Yellow = Color(0xFFFFD166)
val DarkGray = Color(0xFF333333)
val Gray = Color(0xFF777777)
val LightGray = Color(0xFFF5F5F5)


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
                },

                onLoginSuccess = {
                    currentScreen = "publish"
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


        "publish" -> {

            PublishJobScreen(

                onBack = {
                    currentScreen = "login"
                },

                onPublished = {
                    currentScreen = "success"
                }
            )
        }

        "success" -> {

            JobPublishedScreen(

                onViewJob = {
                    currentScreen = "publish"
                },

                onHome = {
                    currentScreen = "home"
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
    onRegister: () -> Unit,
    onLoginSuccess: () -> Unit
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
            modifier = Modifier.height(45.dp)
        )

        Text(
            text = "💼",
            fontSize = 55.sp
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Chambitas",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Purple
        )

        Spacer(
            modifier = Modifier.height(8.dp)
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
            modifier = Modifier.height(5.dp)
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

                // Por ahora simulamos login exitoso.
                // Después conectaremos el backend.
                onLoginSuccess()

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
            color = Gray
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
            modifier = Modifier.height(30.dp)
        )

        Text(
            text = "💼",
            fontSize = 50.sp
        )

        Text(
            text = "Crear cuenta",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Purple
        )

        Spacer(
            modifier = Modifier.height(20.dp)
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
            modifier = Modifier.height(20.dp)
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
                text = "Registrarme"
            )
        }

        Spacer(
            modifier = Modifier.height(10.dp)
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

@Composable
fun PublishJobScreen(
    onBack: () -> Unit,
    onPublished: () -> Unit
) {

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var budget by remember {
        mutableStateOf("")
    }

    var location by remember {
        mutableStateOf("")
    }

    var selectedCategory by remember {
        mutableStateOf("Limpieza")
    }

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    top = 20.dp,
                    end = 20.dp
                ),

            verticalAlignment = Alignment.CenterVertically
        ) {

            TextButton(
                onClick = onBack
            ) {

                Text(
                    text = "←",
                    fontSize = 25.sp,
                    color = DarkGray
                )
            }

            Spacer(
                modifier = Modifier.width(5.dp)
            )

            Text(
                text = "Publicar trabajo",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGray
            )
        }

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    bottom = 20.dp
                )
        ) {

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "¿Qué tipo de trabajo\nnecesitas?",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGray
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )


            Text(
                text = "Título",
                fontWeight = FontWeight.SemiBold,
                color = DarkGray
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            OutlinedTextField(

                value = title,

                onValueChange = {
                    title = it
                },

                modifier = Modifier.fillMaxWidth(),

                placeholder = {
                    Text("Ej. Limpieza de casa")
                },

                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )


            Text(
                text = "Categoría",
                fontWeight = FontWeight.SemiBold,
                color = DarkGray
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                CategoryButton(
                    emoji = "🧹",
                    name = "Limpieza",
                    selected = selectedCategory == "Limpieza",
                    onClick = {
                        selectedCategory = "Limpieza"
                    }
                )

                CategoryButton(
                    emoji = "📦",
                    name = "Mudanzas",
                    selected = selectedCategory == "Mudanzas",
                    onClick = {
                        selectedCategory = "Mudanzas"
                    }
                )

                CategoryButton(
                    emoji = "🌱",
                    name = "Jardinería",
                    selected = selectedCategory == "Jardinería",
                    onClick = {
                        selectedCategory = "Jardinería"
                    }
                )

                CategoryButton(
                    emoji = "•••",
                    name = "Otros",
                    selected = selectedCategory == "Otros",
                    onClick = {
                        selectedCategory = "Otros"
                    }
                )
            }

            Spacer(
                modifier = Modifier.height(15.dp)
            )


            Text(
                text = "Descripción",
                fontWeight = FontWeight.SemiBold,
                color = DarkGray
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            OutlinedTextField(

                value = description,

                onValueChange = {
                    description = it
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .height(85.dp),

                placeholder = {
                    Text("Describe el trabajo que necesitas...")
                }
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )


            Text(
                text = "Presupuesto",
                fontWeight = FontWeight.SemiBold,
                color = DarkGray
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            OutlinedTextField(

                value = budget,

                onValueChange = {
                    budget = it
                },

                modifier = Modifier.fillMaxWidth(),

                placeholder = {
                    Text("$ 0.00")
                },

                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )


            Text(
                text = "Ubicación",
                fontWeight = FontWeight.SemiBold,
                color = DarkGray
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            OutlinedTextField(

                value = location,

                onValueChange = {
                    location = it
                },

                modifier = Modifier.fillMaxWidth(),

                placeholder = {
                    Text("📍 Centro, Oaxaca")
                },

                singleLine = true
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )


            Button(

                onClick = {

                    onPublished()

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
                    text = "Publicar trabajo",
                    fontSize = 16.sp
                )
            }
        }
    }
}


@Composable
fun CategoryButton(
    emoji: String,
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {

    Column(

        modifier = Modifier
            .width(75.dp)
            .clickable {
                onClick()
            }
            .background(
                if (selected) LightPurple
                else Color.Transparent,

                RoundedCornerShape(12.dp)
            )
            .border(

                width = 1.dp,

                color = if (selected)
                    Purple
                else
                    Color.LightGray,

                shape = RoundedCornerShape(12.dp)
            )
            .padding(8.dp),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = emoji,
            fontSize = 23.sp
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = name,
            fontSize = 11.sp,
            color = if (selected)
                Purple
            else
                DarkGray,

            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun JobPublishedScreen(
    onViewJob: () -> Unit,
    onHome: () -> Unit
) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(30.dp),

        horizontalAlignment = Alignment.CenterHorizontally,

        verticalArrangement = Arrangement.Center
    ) {

        Box(

            modifier = Modifier
                .size(110.dp)
                .background(
                    Green,
                    CircleShape
                ),

            contentAlignment = Alignment.Center
        ) {

            Text(
                text = "✓",
                fontSize = 60.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Text(
            text = "¡Trabajo publicado\ncon éxito!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGray,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(15.dp)
        )

        Text(
            text = "Tu trabajo ya está disponible\npara los interesados.",
            fontSize = 16.sp,
            color = Gray,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(35.dp)
        )

        Button(

            onClick = onViewJob,

            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),

            shape = RoundedCornerShape(15.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = Purple
            )
        ) {

            Text(
                text = "Ver trabajo",
                fontSize = 16.sp
            )
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        TextButton(
            onClick = onHome
        ) {

            Text(
                text = "Ir al inicio",
                color = Purple
            )
        }
    }
}