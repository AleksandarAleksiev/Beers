package com.aaleksiev.beer.ui.beerdetails

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.aaleksiev.beer.ui.Dummy
import com.aaleksiev.core.ui.R
import com.aaleksiev.core.ui.UiState.Error
import com.aaleksiev.core.ui.UiState.Loading
import com.aaleksiev.core.ui.UiState.Success
import org.junit.Rule
import org.junit.Test

class BeerDetailsScreenTest {
  @get:Rule
  val composeTestRule = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun showsLoadingSpinner() {
    composeTestRule.setContent {
      BeerDetailsScreen(
        beerName = Dummy.beers[0].name,
        uiState = Loading,
        viewEvent = {},
        navigateUp = {}
      )
    }

    composeTestRule
      .onNodeWithTag(testTag = "IndeterminateProgressIndicator")
      .assertExists()

    composeTestRule
      .onNodeWithText(text = Dummy.beers[0].name)
      .assertExists()
  }

  @Test
  fun showsError() {
    composeTestRule.setContent {
      BeerDetailsScreen(
        beerName = Dummy.beers[0].name,
        uiState = Error,
        viewEvent = {},
        navigateUp = {}
      )
    }

    composeTestRule
      .onNodeWithText(text = composeTestRule.activity.getString(R.string.error_generic_title))
      .assertExists()
  }

  @Test
  fun willShowBeerDetails() {
    composeTestRule.setContent {
      BeerDetailsScreen(
        beerName = Dummy.beers[0].name,
        uiState = Success(
          data = Dummy.beers[0]
        ),
        viewEvent = {},
        navigateUp = {}
      )
    }

    composeTestRule
      .onAllNodes(
        hasText(Dummy.beers[0].tagline)
          .and(hasText(Dummy.beers[0].description))
          .and(hasText(Dummy.beers[0].ingredients.hops[0].name))
      )
      .assertAll(
        hasText(Dummy.beers[0].tagline)
          .and(hasText(Dummy.beers[0].description))
          .and(hasText(Dummy.beers[0].ingredients.hops[0].name))
      )
  }
}