package com.funapp.pookiemon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.funapp.pookiemon.core.config.navigation.AppNavGraph
import com.funapp.pookiemon.core.data.settings.DarkModePreference
import com.funapp.pookiemon.core.data.settings.SettingsDataStore
import com.funapp.pookiemon.core.theme.PookieMonTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settingsDataStore: SettingsDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val paletteName by settingsDataStore.paletteName.collectAsState("default")
            val darkModePref by settingsDataStore.darkMode.collectAsState(DarkModePreference.SYSTEM)
            val isDark = when (darkModePref) {
                DarkModePreference.LIGHT -> false
                DarkModePreference.DARK -> true
                DarkModePreference.SYSTEM -> isSystemInDarkTheme()
            }
            PookieMonTheme(
                paletteName = paletteName,
                darkTheme = isDark,
            ) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AppNavGraph()
                }
            }
        }
    }
}
