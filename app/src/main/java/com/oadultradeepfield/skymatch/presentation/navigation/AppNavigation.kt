package com.oadultradeepfield.skymatch.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oadultradeepfield.skymatch.presentation.upload.UploadScreen

/** Main navigation graph for the SkyMatch application. */
@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
) {
  val navController = rememberNavController()

  NavHost(
      navController = navController,
      startDestination = "upload",
      modifier = modifier,
  ) {
    composable("upload") {
      UploadScreen(
          onNavigateToSolving = { imageUris ->
            navController.navigate("solving/${imageUris.joinToString(",")}")
          }
      )
    }
  }
}
