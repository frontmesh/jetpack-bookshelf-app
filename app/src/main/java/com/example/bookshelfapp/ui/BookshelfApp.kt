package com.example.bookshelfapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.bookshelfapp.ui.screens.BookshelfViewModel
import com.example.bookshelfapp.ui.screens.HomeScreen
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bookshelfapp.ui.screens.DetailsBookScreen

@Composable
fun BookshelfAppBar(
    currentScreen: BookshelfRoutes,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
            }

        },
        modifier = modifier
    )
}

@Composable
fun BookshelfApp(
    viewModel: BookshelfViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = BookshelfRoutes.valueOf( backStackEntry?.destination?.route ?: BookshelfRoutes.Start.name)
    Scaffold(
        topBar = {
            BookshelfAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {

            NavHost(
                navController = navController,
                startDestination = BookshelfRoutes.Start.name
            ) {
                composable(BookshelfRoutes.Start.name) {
                    HomeScreen(
                        viewModel,
                        onDetailsClick = { book ->
                            viewModel.selectedBookId = book.id
                            viewModel.getBookDetails()
                            navController.navigate(BookshelfRoutes.Details.name)
                        }
                    )
                }

                composable(
                    route = BookshelfRoutes.Details.name,
                ) {
                    DetailsBookScreen(
                        viewModel
                    )
                }
            }
        }

    }
}