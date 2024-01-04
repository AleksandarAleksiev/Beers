package com.aaleksiev.beer.ui.beerdetails

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.aaleksiev.beer.ui.BeersViewEvent.Reload
import com.aaleksiev.beer.ui.CoroutinesTestRule
import com.aaleksiev.beer.ui.Dummy
import com.aaleksiev.beer.ui.navigation.BeersNavigation.BEER_ID_ARG
import com.aaleksiev.core.data.beer.BeerRepository
import com.aaleksiev.core.ui.UiState.Error
import com.aaleksiev.core.ui.UiState.Success
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val BEER_ID = 1L

class BeerDetailsViewModelTest {
  @OptIn(ExperimentalCoroutinesApi::class)
  @get:Rule
  val coroutinesTestRule = CoroutinesTestRule()

  @MockK
  private lateinit var beerRepository: BeerRepository

  @MockK
  private lateinit var savedStateHandle: SavedStateHandle

  private lateinit var underTest: BeerDetailsViewModel

  @Before
  fun setUp() {
    MockKAnnotations.init(this)
    every {
      savedStateHandle.getStateFlow(key = BEER_ID_ARG, -1L)
    } returns MutableStateFlow(BEER_ID)

    underTest = BeerDetailsViewModel(
      beerRepository = beerRepository,
      savedStateHandle = savedStateHandle,
    )
  }

  @Test
  fun willLoadBeers() = runTest {
    every { beerRepository.beerDetails(BEER_ID) } returns flowOf(Result.success(Dummy.beers[0]))
    underTest.beerDetailsUiState.test {
      assertEquals(Success(Dummy.beers[0]), awaitItem())
    }

    verify(exactly = 1) { beerRepository.beerDetails(BEER_ID) }
  }

  @Test
  fun willEmitErrorState() = runTest {
    every {
      beerRepository.beerDetails(BEER_ID)
    } returns flowOf(Result.failure(UnsupportedOperationException()))

    underTest.beerDetailsUiState.test {
      assertEquals(Error, awaitItem())
    }

    verify(exactly = 1) { beerRepository.beerDetails(BEER_ID) }
  }

  @Test
  fun willReloadBeers() = runTest {
    every {
      beerRepository.beerDetails(BEER_ID)
    } returns flowOf(Result.failure(UnsupportedOperationException())) andThen
      flowOf(Result.success(Dummy.beers[0]))

    underTest.beerDetailsUiState.test {
      TestCase.assertEquals(Error, awaitItem())
    }

    underTest.setEvent(Reload)

    underTest.beerDetailsUiState.test {
      TestCase.assertEquals(Success(Dummy.beers[0]), awaitItem())
    }

    verify(exactly = 2) { beerRepository.beerDetails(BEER_ID) }
  }
}