package com.aaleksiev.beer.ui.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.aaleksiev.beer.ui.beer.BeersScreen
import com.aaleksiev.beer.ui.beer.BeersViewModel
import com.aaleksiev.beer.ui.beerdetails.BeerDetailsScreen
import com.aaleksiev.beer.ui.beerdetails.BeerDetailsViewModel

object BeersNavigation {
  const val BEERS = "Beers"
  private const val BEERS_SCREEN_ROUTE = "BeersScreen"
  internal const val BEER_ID_ARG = "beer_id_arg"
  private const val BEER_NAME_ARG = "beer_name_arg"
  private const val BEER_DETAILS_SCREEN_ROUTE = "beer_details_screen/" +
    "{$BEER_ID_ARG}/" +
    "{$BEER_NAME_ARG}"

  fun NavGraphBuilder.beersNavGraph(navController: NavController) {
    navigation(startDestination = BEERS_SCREEN_ROUTE, route = BEERS) {
      beersScreen(navController)
      beerDetailsScreen(navController)
    }
  }

  private fun NavGraphBuilder.beersScreen(navController: NavController) {
    composable(route = BEERS_SCREEN_ROUTE) {
      val beersViewModel: BeersViewModel = hiltViewModel()
      val uiState by beersViewModel.beersUiState.collectAsStateWithLifecycle()
      BeersScreen(
        uiState = uiState,
        viewEvent = beersViewModel::setEvent,
        openBeerDetails = { beerId, beerName ->
          navController.openBeerDetails(beerId, beerName)
        }
      )
    }
  }

  private fun NavGraphBuilder.beerDetailsScreen(navController: NavController) {
    composable(
      route = BEER_DETAILS_SCREEN_ROUTE,
      arguments = listOf(
        navArgument(BEER_ID_ARG) { type = NavType.LongType },
        navArgument(BEER_NAME_ARG) { type = NavType.StringType },
      )
    ) { navBackStackEntry ->
      val beersViewModel: BeerDetailsViewModel = hiltViewModel()
      val uiState by beersViewModel.beerDetailsUiState.collectAsStateWithLifecycle()
      val beerName = navBackStackEntry.arguments?.getString(BEER_NAME_ARG).orEmpty()
      BeerDetailsScreen(
        beerName = beerName,
        uiState = uiState,
        viewEvent = beersViewModel::setEvent,
        navigateUp = navController::navigateUp
      )
    }
  }

  private fun NavController.openBeerDetails(beerId: Long, beerName: String) {
    navigate(
      route = BEER_DETAILS_SCREEN_ROUTE
        .replace("{$BEER_ID_ARG}", "$beerId")
        .replace("{$BEER_NAME_ARG}", beerName)
    )
  }
}