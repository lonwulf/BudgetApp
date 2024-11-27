package com.lonwulf.budgetapp.ui.screens

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.lonwulf.budgetapp.navigation.NavComposable

class NotificationScreenComposable:NavComposable{
    @Composable
    override fun Composable(
        navHostController: NavHostController,
        snackbarHostState: SnackbarHostState
    ) {
        NotificationScreen()
    }

}

@Composable
fun NotificationScreen() {

}