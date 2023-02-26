package com.github.minimi.stroycalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.DisposableEffect
import androidx.core.view.WindowCompat
import com.github.minimi.stroycalc.designsystem.theme.StroyCalcTheme
import com.github.minimi.stroycalc.ui.StroyApp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val systemUiController = rememberSystemUiController()
            val darkTheme = isSystemInDarkTheme()

            // Turn off the decor fitting system windows, which allows us to handle insets,
            // including IME animations
            WindowCompat.setDecorFitsSystemWindows(window, false)

            // Update the dark content of the system bars to match the theme
            DisposableEffect(systemUiController, darkTheme) {
                systemUiController.systemBarsDarkContentEnabled = !darkTheme
                onDispose {}
            }

            StroyCalcTheme(darkTheme = darkTheme) {
                StroyApp(windowSizeClass = calculateWindowSizeClass(activity = this))
            }
        }
    }
}
