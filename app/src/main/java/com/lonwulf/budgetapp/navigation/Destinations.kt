package com.lonwulf.budgetapp.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

object DestinationsConstants {
    const val HOMEPAGE_SCREEN = "_HOMEPAGE_SCREEN"
    const val BUDGET_SCREEN = "_BUDGET_SCREEN"
    const val REPORT_SCREEN = "_REPORT_SCREEN"
    const val ACCOUNT_SCREEN = "_ACCOUNT_SCREEN"
    const val SUCCESS_SCREEN = "_SUCCESS_SCREEN"
    const val NOTIFICATIONS_SCREEN = "_NOTIFICATIONS_SCREEN"
}

sealed class Destinations(val route:String, val title:String){
    object SuccessScreen:Destinations(DestinationsConstants.SUCCESS_SCREEN, "Success Screen")
    object NotificationScreen:Destinations(DestinationsConstants.NOTIFICATIONS_SCREEN, "Notifications")
}

sealed class TopLevelDestinations(val route: String, val title: String, val icon:ImageVector){
    object HomepageScreen:TopLevelDestinations(DestinationsConstants.HOMEPAGE_SCREEN, "Homepage", Icons.Filled.Home)
    object BudgetScreen:TopLevelDestinations(DestinationsConstants.BUDGET_SCREEN, "Budget", Icons.Filled.Home)
    object ReportScreen:TopLevelDestinations(DestinationsConstants.REPORT_SCREEN, "Report", Icons.Filled.Home)
    object AccountScreen:TopLevelDestinations(DestinationsConstants.ACCOUNT_SCREEN, "Account", Icons.Filled.Person)
}