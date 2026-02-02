package com.oadultradeepfield.skymatch.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oadultradeepfield.skymatch.presentation.home.HomeScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
  val navController = rememberNavController()

  // TODO: Add other destinations and implement real navigation
  NavHost(
      navController = navController,
      startDestination = "home",
      modifier = modifier.fillMaxSize(),
  ) {
    composable("home") { HomeScreen(onNavigateToUpload = {}) }
  }
}
