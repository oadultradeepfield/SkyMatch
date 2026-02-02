package com.oadultradeepfield.skymatch.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.oadultradeepfield.skymatch.presentation.navigation.AppNavigation
import com.oadultradeepfield.skymatch.presentation.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main entry point for the SkyMatch application. Hosts the navigation graph and applies the app
 * theme.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent { AppTheme { AppNavigation() } }
  }
}
