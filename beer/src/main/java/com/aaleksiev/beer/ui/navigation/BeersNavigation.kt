package com.aaleksiev.beer.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.aaleksiev.beer.ui.beer.BeersScreen
import com.aaleksiev.beer.ui.beer.BeersViewModel

object BeersNavigation {
    const val Beers = "Beers"
    fun NavGraphBuilder.beersNavGraph(navController: NavController) {
        navigation(startDestination = "BeersScreen", route = Beers) {
            beersScreen()
            beerDetailsScreen()
        }
    }

    private fun NavGraphBuilder.beersScreen() {
        composable(route = "BeersScreen") {
            val beersViewModel: BeersViewModel = hiltViewModel()
            val uiState by beersViewModel.beersUiState.collectAsStateWithLifecycle()
            BeersScreen(uiState)
        }
    }

    private fun NavGraphBuilder.beerDetailsScreen() {
        composable(route = "BeerDetailsScreen") {

        }
    }
}