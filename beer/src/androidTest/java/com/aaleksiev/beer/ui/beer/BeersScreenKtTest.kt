package com.aaleksiev.beer.ui.beer

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.aaleksiev.beer.ui.Dummy
import com.aaleksiev.core.ui.R
import com.aaleksiev.core.ui.UiState
import org.junit.Rule
import org.junit.Test

class BeersScreenKtTest {

  @get:Rule
  val composeTestRule = createAndroidComposeRule<ComponentActivity>()

  @Test
  fun showsLoadingSpinner() {
    composeTestRule.setContent {
      BeersScreen(
        uiState = UiState.Loading,
        viewEvent = {},
        openBeerDetails = { _, _ -> }
      )
    }

    composeTestRule
      .onNodeWithTag(testTag = "IndeterminateProgressIndicator")
      .assertExists()
  }

  @Test
  fun showsError() {
    composeTestRule.setContent {
      BeersScreen(
        uiState = UiState.Error,
        viewEvent = {},
        openBeerDetails = { _, _ -> }
      )
    }

    composeTestRule
      .onNodeWithText(text = composeTestRule.activity.getString(R.string.error_generic_title))
      .assertExists()
  }

  @Test
  fun willShowBeers() {
    composeTestRule.setContent {
      BeersScreen(
        uiState = UiState.Success(
          data = Dummy.beers
        ),
        viewEvent = {},
        openBeerDetails = { _, _ -> }
      )
    }

    composeTestRule
      .onNodeWithText(text = Dummy.beers[0].name)
      .assertExists()
  }
}
