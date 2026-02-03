package com.oadultradeepfield.skymatch.presentation.navigation

import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.oadultradeepfield.skymatch.presentation.home.HomeScreen
import com.oadultradeepfield.skymatch.presentation.search.SearchScreen
import com.oadultradeepfield.skymatch.presentation.solving.SolvingScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
  val navController = rememberNavController()

  NavHost(
      navController = navController,
      startDestination = "home",
      modifier = modifier.fillMaxSize(),
  ) {
    composable("home") {
      HomeScreen(
          onNavigateToUpload = { imageUris ->
            val encodedUris = imageUris.joinToString(",") { Uri.encode(it) }
            navController.navigate("solving/$encodedUris")
          },
          onNavigateToSearch = { navController.navigate("search") },
      )
    }
    composable("search") { SearchScreen(onNavigateBack = { navController.popBackStack() }) }
    composable(
        route = "solving/{imageUris}",
        arguments = listOf(navArgument("imageUris") { type = NavType.StringType }),
    ) { backStackEntry ->
      val encodedUris = backStackEntry.arguments?.getString("imageUris") ?: ""
      val imageUris = encodedUris.split(",").map { Uri.decode(it) }
      SolvingScreen(
          imageUris = imageUris,
          onNavigateBack = { navController.popBackStack() },
      )
    }
  }
}
