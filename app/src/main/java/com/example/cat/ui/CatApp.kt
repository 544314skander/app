package com.example.cat.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cat.ui.screens.DetailScreen
import com.example.cat.ui.screens.HomeScreen

@Composable
fun CatApp(viewModel: CatViewModel) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") {
            HomeScreen(
                state = uiState,
                onCatSelected = { id -> navController.navigate("details/$id") },
                onToggleFavorite = viewModel::toggleFavorite,
                onToggleFilter = viewModel::toggleFavoritesFilter
            )
        }

        composable(
            route = "details/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { entry ->
            val id = entry.arguments?.getInt("id") ?: return@composable
            val cat = viewModel.getCat(id)
            if (cat != null) {
                DetailScreen(
                    cat = cat,
                    onBack = { navController.popBackStack() },
                    onToggleFavorite = { viewModel.toggleFavorite(id) }
                )
            } else {
                // Fallback UI if an invalid id is passed
                HomeScreen(
                    state = uiState,
                    onCatSelected = { next -> navController.navigate("details/$next") },
                    onToggleFavorite = viewModel::toggleFavorite,
                    onToggleFilter = viewModel::toggleFavoritesFilter
                )
            }
        }
    }
}
