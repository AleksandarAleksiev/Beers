package com.aaleksiev.beer.ui.beer

import app.cash.turbine.test
import com.aaleksiev.beer.ui.BeersViewEvent.Reload
import com.aaleksiev.core.data.beer.BeerRepository
import com.aaleksiev.core.ui.UiState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BeersViewModelTest {
  @OptIn(ExperimentalCoroutinesApi::class)
  @get:Rule
  val coroutinesTestRule = CoroutinesTestRule()

  @MockK
  private lateinit var beersRepository: BeerRepository

  private lateinit var underTest: BeersViewModel

  @Before
  fun setUp() {
    MockKAnnotations.init(this)
    underTest = BeersViewModel(beersRepository)
  }

  @Test
  fun willLoadBeers() = runTest {
    every { beersRepository.beers() } returns flowOf(Result.success(Dummy.beers))
    underTest.beersUiState.test {
      assertEquals(UiState.Success(Dummy.beers), awaitItem())
    }

    verify(exactly = 1) { beersRepository.beers() }
  }

  @Test
  fun willEmitErrorState() = runTest {
    every { beersRepository.beers() } returns flowOf(Result.failure(UnsupportedOperationException()))
    underTest.beersUiState.test {
      assertEquals(UiState.Error, awaitItem())
    }

    verify(exactly = 1) { beersRepository.beers() }
  }

  @Test
  fun willReloadBeers() = runTest {
    every {
      beersRepository.beers()
    } returns flowOf(Result.failure(UnsupportedOperationException())) andThen
      flowOf(Result.success(Dummy.beers))

    underTest.beersUiState.test {
      assertEquals(UiState.Error, awaitItem())
    }

    underTest.setEvent(Reload)

    underTest.beersUiState.test {
      assertEquals(UiState.Success(Dummy.beers), awaitItem())
    }

    verify(exactly = 2) { beersRepository.beers() }
  }
}