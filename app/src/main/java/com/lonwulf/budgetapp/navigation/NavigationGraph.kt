package com.lonwulf.budgetapp.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable

interface NavComposable {
    @Composable
    fun Composable(navHostController: NavHostController, snackbarHostState: SnackbarHostState)
}

@Composable
fun NavigationGraph(
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState,
    composable: Map<String, NavComposable>
) {
    NavHost(navController = navHostController, startDestination = TopLevelDestinations.HomepageScreen.route){
        composable.forEach {(route, composable)->
            composable(route = route){
                composable.Composable(navHostController, snackbarHostState)
            }
        }
    }
}