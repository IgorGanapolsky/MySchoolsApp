package com.cnh.samples.apps.schools

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.cnh.samples.apps.schools.compose.CNHApp
import com.cnh.samples.apps.schools.ui.CNHTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // Displaying edge-to-edge
        enableEdgeToEdge()
        setContent {
            CNHTheme {
                CNHApp()
            }
        }
    }
}
