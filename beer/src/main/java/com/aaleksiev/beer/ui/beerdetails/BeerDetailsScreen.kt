package com.aaleksiev.beer.ui.beerdetails

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.aaleksiev.beer.ui.BeersViewEvent
import com.aaleksiev.beer.ui.BeersViewEvent.Reload
import com.aaleksiev.core.model.Beer
import com.aaleksiev.core.ui.UiState
import com.aaleksiev.core.ui.UiState.Error
import com.aaleksiev.core.ui.UiState.Loading
import com.aaleksiev.core.ui.appbar.SimpleTopAppBar
import com.aaleksiev.core.ui.error.RetryableError
import com.aaleksiev.core.ui.insets.noInsets
import com.aaleksiev.core.ui.progress.IndeterminateProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BeerDetailsScreen(
  beerName: String,
  uiState: UiState<Beer>,
  viewEvent: (BeersViewEvent) -> Unit,
  navigateUp: () -> Unit,
) {
  val scrollBehaviour = TopAppBarDefaults.pinnedScrollBehavior()
  Scaffold(
    modifier = Modifier
      .fillMaxSize()
      .nestedScroll(connection = scrollBehaviour.nestedScrollConnection),
    topBar = {
      SimpleTopAppBar(
        title = beerName,
        scrollBehavior = scrollBehaviour,
        navigateUp = navigateUp
      )
    },
    contentWindowInsets = WindowInsets.noInsets,
  ) { paddings ->
    Crossfade(targetState = uiState, label = "BeerDetailsScreenCrossFade") { state ->
      when (state) {
        is Loading -> IndeterminateProgressIndicator(modifier = Modifier.padding(paddings))
        is Error -> RetryableError(
          modifier = Modifier
            .padding(paddings)
            .fillMaxWidth(),
          retry = { viewEvent(Reload) },
        )
        else -> {}
      }
    }
  }
}