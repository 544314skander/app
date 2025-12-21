package com.example.cat.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Map
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cat.NavTarget
import com.example.cat.ui.screens.CameraScreen
import com.example.cat.ui.screens.DetailScreen
import com.example.cat.ui.screens.HomeScreen
import com.example.cat.ui.screens.MapScreen

@Composable
fun CatApp(
    viewModel: CatViewModel,
    navTarget: NavTarget?
) {
    val navController = rememberNavController()
    val uiState by viewModel.uiState.collectAsState()

    val tabs = listOf(
        BottomNavItem("home", "Cats", Icons.Rounded.Home),
        BottomNavItem("camera", "Camera", Icons.Rounded.CameraAlt),
        BottomNavItem("map", "Map", Icons.Rounded.Map)
    )

    LaunchedEffect(navTarget) {
        when (val target = navTarget) {
            NavTarget.Home -> navController.popBackStack("home", inclusive = false)
            is NavTarget.CatDetail -> navController.navigate("details/${target.id}") {
                launchSingleTop = true
            }
            NavTarget.Tools -> navController.navigate("map") { launchSingleTop = true }
            null -> Unit
        }
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    androidx.compose.material3.Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEach { item ->
                    val selected = currentRoute?.startsWith(item.route) == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo("home") { inclusive = false }
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        colors = NavigationBarItemDefaults.colors()
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("home") {
                HomeScreen(
                    state = uiState,
                    onCatSelected = { id -> navController.navigate("details/$id") },
                    onOpenTools = { navController.navigate("map") },
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
                    HomeScreen(
                        state = uiState,
                        onCatSelected = { next -> navController.navigate("details/$next") },
                        onOpenTools = { navController.navigate("map") },
                        onToggleFavorite = viewModel::toggleFavorite,
                        onToggleFilter = viewModel::toggleFavoritesFilter
                    )
                }
            }

            composable("camera") {
                CameraScreen(
                    cats = uiState.cats,
                    onAttachPhoto = viewModel::attachPhoto
                )
            }

            composable("map") {
                MapScreen()
            }
        }
    }
}

private data class BottomNavItem(val route: String, val label: String, val icon: ImageVector)
