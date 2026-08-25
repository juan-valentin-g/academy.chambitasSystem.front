package com.example.chambitassystemfront

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.chambitassystemfront.data.session.SessionManager
import com.example.chambitassystemfront.ui.navigation.AppNavigation
import com.example.chambitassystemfront.ui.theme.ChambitasSystemFrontTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionManager.initialize(applicationContext)

        setContent {
            ChambitasSystemFrontTheme {
                AppNavigation()
            }
        }
    }
}
