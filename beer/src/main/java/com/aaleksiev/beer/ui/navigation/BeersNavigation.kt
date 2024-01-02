package com.aaleksiev.beer.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.aaleksiev.beer.ui.beer.BeersScreen

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
            BeersScreen()
        }
    }

    private fun NavGraphBuilder.beerDetailsScreen() {
        composable(route = "BeerDetailsScreen") {

        }
    }
}