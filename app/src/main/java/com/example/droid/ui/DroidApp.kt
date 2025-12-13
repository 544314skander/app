package com.example.droid.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.droid.ui.screens.DetailScreen
import com.example.droid.ui.screens.HomeScreen

@Composable
fun DroidApp(viewModel: DroidViewModel) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                state = uiState,
                onDroidSelected = { id -> navController.navigate("details/$id") },
                onToggleFavorite = viewModel::toggleFavorite,
                onToggleFilter = viewModel::toggleFavoritesFilter
            )
        }

        composable(
            route = "details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: return@composable
            val droid = viewModel.getDroid(id)
            if (droid != null) {
                DetailScreen(
                    droid = droid,
                    onBack = { navController.popBackStack() },
                    onToggleFavorite = { viewModel.toggleFavorite(id) }
                )
            } else {
                // Fallback UI if an invalid id is passed
                HomeScreen(
                    state = uiState,
                    onDroidSelected = { next -> navController.navigate("details/$next") },
                    onToggleFavorite = viewModel::toggleFavorite,
                    onToggleFilter = viewModel::toggleFavoritesFilter
                )
            }
        }
    }
}
