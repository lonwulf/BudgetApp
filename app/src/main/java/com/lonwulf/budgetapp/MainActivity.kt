package com.lonwulf.budgetapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.lonwulf.budgetapp.navigation.Destinations
import com.lonwulf.budgetapp.navigation.NavigationGraph
import com.lonwulf.budgetapp.navigation.TopLevelDestinations
import com.lonwulf.budgetapp.ui.screens.AccountScreenComposable
import com.lonwulf.budgetapp.ui.screens.BudgetScreenComposable
import com.lonwulf.budgetapp.ui.screens.HomepageScreenComposable
import com.lonwulf.budgetapp.ui.screens.NotificationScreenComposable
import com.lonwulf.budgetapp.ui.screens.ReportScreenComposable
import com.lonwulf.budgetapp.ui.screens.SuccessScreenComposable
import com.lonwulf.budgetapp.ui.theme.BudgetAppTheme
import com.lonwulf.budgetapp.ui.theme.lightGreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BudgetAppTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                val snackbarHostState = remember { SnackbarHostState() }
                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavigation(
                            navHostController = navController,
                            currentDestination = currentDestination
                        )
                    },
                    topBar = {
                        Column {
                            Toolbar("")
                        }
                    }
                ) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        val composables = mapOf(
                            Destinations.SuccessScreen.route to SuccessScreenComposable(),
                            Destinations.NotificationScreen.route to NotificationScreenComposable(),
                            TopLevelDestinations.HomepageScreen.route to HomepageScreenComposable(),
                            TopLevelDestinations.BudgetScreen.route to BudgetScreenComposable(),
                            TopLevelDestinations.ReportScreen.route to ReportScreenComposable(),
                            TopLevelDestinations.AccountScreen.route to AccountScreenComposable()
                        )
                        NavigationGraph(
                            navHostController = navController,
                            composable = composables,
                            snackbarHostState = snackbarHostState
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun BottomNavigation(
        navHostController: NavHostController,
        currentDestination: NavDestination?
    ) {
        val screens = listOf(
            TopLevelDestinations.HomepageScreen,
            TopLevelDestinations.BudgetScreen,
            TopLevelDestinations.ReportScreen,
            TopLevelDestinations.AccountScreen
        )
        Box {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                tonalElevation = 5.dp
//                modifier = Modifier.height(72.dp)
            ) {
                screens.forEachIndexed { index, screen ->
                    if (index == 2) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    AddItem(
                        screen = screen,
                        currentDestination = currentDestination,
                        navHostController = navHostController
                    )
                }
            }
            CenterFloatingActionButton(navHostController = navHostController)
        }
    }

    @Composable
    fun RowScope.AddItem(
        screen: TopLevelDestinations,
        currentDestination: NavDestination?,
        navHostController: NavHostController
    ) {
        NavigationBarItem(
            label = {
                Text(
                    text = screen.title,
                    color = if (currentDestination?.route == screen.route) Color(0xFF77A03E) else Color.Gray
                )
            },
            colors = NavigationBarItemColors(
                selectedIconColor = Color.White,
                selectedTextColor = Color.White,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                selectedIndicatorColor = MaterialTheme.colorScheme.background,
                disabledIconColor = Color.Gray,
                disabledTextColor = Color.Gray
            ),
            selected = currentDestination?.route == screen.route,
            onClick = {
                navHostController.navigate(screen.route) {
                    popUpTo(navHostController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            },
            icon = {
                BadgedBox(badge = {}) {
                    Icon(
                        imageVector = screen.icon, contentDescription = "bottom bar icon",
                        tint = if (currentDestination?.route == screen.route) Color(0xFF77A03E) else Color.Gray
                    )
                }
            })
    }

    @Composable
    fun CenterFloatingActionButton(navHostController: NavHostController) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            FloatingActionButton(
                onClick = {
                    navHostController.navigate(TopLevelDestinations.BudgetScreen.route)
                },
                containerColor = lightGreen,
                modifier = Modifier.size(64.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun Toolbar(title: String) {
        TopAppBar(
            title = { Text(text = title) }, actions = {},
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                scrolledContainerColor = colorResource(
                    id = R.color.white
                ),
                navigationIconContentColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.background,
                actionIconContentColor = colorResource(
                    id = R.color.white
                )
            ),
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BudgetAppTheme {
        Greeting("Android")
    }
}