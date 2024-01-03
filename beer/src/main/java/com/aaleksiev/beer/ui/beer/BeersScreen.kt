package com.aaleksiev.beer.ui.beer

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aaleksiev.beers.R.string
import com.aaleksiev.core.designsystem.ui.theme.paddings
import com.aaleksiev.core.model.Beer
import com.aaleksiev.core.ui.UiState
import com.aaleksiev.core.ui.UiState.Error
import com.aaleksiev.core.ui.UiState.Loading
import com.aaleksiev.core.ui.UiState.Success
import com.aaleksiev.core.ui.appbar.SimpleTopAppBar
import com.aaleksiev.core.ui.error.RetryableError
import com.aaleksiev.core.ui.insets.noInsets
import com.aaleksiev.core.ui.progress.IndeterminateProgressIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BeersScreen(
  uiState: UiState<List<Beer>>,
  retry: () -> Unit,
) {
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    topBar = {
      SimpleTopAppBar(
        title = stringResource(id = string.beers),
        navigationIcon = null,
      )
    },
    contentWindowInsets = WindowInsets.noInsets,
  ) { paddings ->
    Crossfade(targetState = uiState, label = "BeersScreenCrossFade") { state ->
      when (state) {
        is Loading -> IndeterminateProgressIndicator(modifier = Modifier.padding(paddings))
        is Error -> RetryableError(
          modifier = Modifier
            .padding(paddings)
            .fillMaxWidth(),
          retry = retry,
        )

        is Success -> BeersList(
          modifier = Modifier.padding(paddings),
          beers = state.data
        )
      }
    }
  }
}

@Composable
private fun BeersList(
  modifier: Modifier = Modifier,
  beers: List<Beer>
) {
  LazyColumn(
    modifier = modifier,
    contentPadding = PaddingValues(
      start = MaterialTheme.paddings.large,
      end = MaterialTheme.paddings.large,
      top = MaterialTheme.paddings.large,
      bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
    ),
    verticalArrangement = Arrangement.spacedBy(MaterialTheme.paddings.small),
  ) {
    items(items = beers, key = { beer -> beer.id }) { beer ->
      BeerItem(beer)
    }
  }
}

@Composable
private fun BeerItem(beer: Beer) {
  Card(
    modifier = Modifier.fillMaxWidth(),
  ) {
    Row(
      modifier = Modifier.padding(MaterialTheme.paddings.small),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      AsyncImage(
        modifier = Modifier.size(size = 40.dp),
        model = beer.imageUrl,
        contentDescription = stringResource(id = string.beer_image_description, beer.name)
      )
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = MaterialTheme.paddings.small)
      ) {
        Text(
          text = beer.name,
          style = MaterialTheme.typography.titleMedium
        )
        Text(
          text = beer.description,
          style = MaterialTheme.typography.bodySmall,
          overflow = TextOverflow.Ellipsis,
          maxLines = 3,
        )
      }
    }
  }
}