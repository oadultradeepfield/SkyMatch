package com.oadultradeepfield.skymatch.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.oadultradeepfield.skymatch.presentation.home.HomeScreen
import com.oadultradeepfield.skymatch.presentation.search.SearchScreen
import com.oadultradeepfield.skymatch.presentation.solving.SolvingScreen

/** Main navigation host using type-safe routes. */
@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
  val navController = rememberNavController()

  NavHost(
      navController = navController,
      startDestination = Route.Home,
      modifier = modifier.fillMaxSize(),
  ) {
    composable<Route.Home> {
      HomeScreen(
          onNavigateToUpload = { imageUris -> navController.navigate(Route.Solving(imageUris)) },
          onNavigateToSearch = { navController.navigate(Route.Search) },
          onNavigateToSolving = { historyId ->
            navController.navigate(Route.Solving(historyId = historyId))
          },
      )
    }

    composable<Route.Search> {
      SearchScreen(
          onNavigateBack = { navController.popBackStack() },
          onNavigateToSolving = { imageUris -> navController.navigate(Route.Solving(imageUris)) },
      )
    }

    composable<Route.Solving> { backStackEntry ->
      val route = backStackEntry.toRoute<Route.Solving>()
      SolvingScreen(
          imageUris = route.imageUris,
          historyId = route.historyId,
          onNavigateBack = { navController.popBackStack() },
          onNavigateToHistory = {
            navController.navigate(Route.Home) { popUpTo(Route.Home) { inclusive = true } }
          },
      )
    }
  }
}
