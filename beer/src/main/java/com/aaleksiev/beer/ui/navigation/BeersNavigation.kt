package com.aaleksiev.beer.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

object BeersNavigation {
    fun NavController.openBeers() {
        navigate(route = "Beers") {
            launchSingleTop = true
        }
    }
    fun NavGraphBuilder.beersNavGraph() {
        navigation(startDestination = "BeersScreen", route = "Beers") {
            beersScreen()
            beerDetailsScreen()
        }
    }

    private fun NavGraphBuilder.beersScreen() {
        composable(route = "BeersScreen") {

        }
    }

    private fun NavGraphBuilder.beerDetailsScreen() {
        composable(route = "BeerDetailsScreen") {

        }
    }
}