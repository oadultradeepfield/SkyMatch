package com.oadultradeepfield.skymatch.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
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
    setContent {
      AppTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = MaterialTheme.colorScheme.background,
        ) { innerPadding ->
          AppNavigation(modifier = Modifier.padding(innerPadding))
        }
      }
    }
  }
}
