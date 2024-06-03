package com.news.headlines

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import com.news.designsystem.theme.NewsTheme
import com.news.util.BiometricAuth
import com.news.util.FlavorChecker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            NewsTheme {
                val context = LocalContext.current
                val biometricAuth = remember { BiometricAuth(context) }
                var isAuthenticated by remember { mutableStateOf(false) }
                val isBiometricSupported by remember { mutableStateOf(biometricAuth.canAuthenticate()) }

                LaunchedEffect(Unit) {
                    if (isBiometricSupported && FlavorChecker.isFullFlavor()) {
                        biometricAuth.authenticate(
                            onSuccess = { isAuthenticated = true },
                            onFailure = { /* Handle failure if necessary */ }
                        )
                    } else {
                        isAuthenticated = true
                    }
                }

                if (isAuthenticated) {
                    AppNavHost()
                }
            }
        }
    }
}
