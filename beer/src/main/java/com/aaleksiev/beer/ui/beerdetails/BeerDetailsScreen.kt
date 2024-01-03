package com.aaleksiev.beer.ui.beerdetails

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.aaleksiev.beer.ui.BeersViewEvent
import com.aaleksiev.beer.ui.BeersViewEvent.Reload
import com.aaleksiev.beers.R.string
import com.aaleksiev.core.designsystem.ui.theme.BeersTheme
import com.aaleksiev.core.designsystem.ui.theme.paddings
import com.aaleksiev.core.model.Amount
import com.aaleksiev.core.model.Beer
import com.aaleksiev.core.model.Ingredient
import com.aaleksiev.core.model.Ingredients
import com.aaleksiev.core.ui.UiState
import com.aaleksiev.core.ui.UiState.Error
import com.aaleksiev.core.ui.UiState.Loading
import com.aaleksiev.core.ui.UiState.Success
import com.aaleksiev.core.ui.appbar.SimpleTopAppBar
import com.aaleksiev.core.ui.error.RetryableError
import com.aaleksiev.core.ui.insets.noInsets
import com.aaleksiev.core.ui.preview.ThemePreview
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

        is Success -> BeerDetails(
          modifier = Modifier
            .padding(paddings)
            .fillMaxWidth()
            .padding(all = MaterialTheme.paddings.large),
          beer = state.data
        )
      }
    }
  }
}

@Composable
private fun BeerDetails(
  modifier: Modifier = Modifier,
  beer: Beer
) {
  Card(modifier = modifier.fillMaxWidth()) {
    Row(
      modifier = Modifier
        .padding(MaterialTheme.paddings.medium)
        .fillMaxWidth(),
    ) {
      AsyncImage(
        modifier = Modifier.size(size = 40.dp),
        model = beer.imageUrl,
        contentDescription = stringResource(id = string.beer_image_description, beer.name)
      )
      Column(
        modifier = Modifier.padding(start = MaterialTheme.paddings.small),
        verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.paddings.small)
      ) {
        Text(
          text = beer.tagline,
          style = MaterialTheme.typography.titleMedium
        )
        Text(
          text = beer.description,
          style = MaterialTheme.typography.bodySmall,
        )
        BeerIngredients(
          sectionName = stringResource(id = string.hops),
          ingredients = beer.ingredients.hops,
        )
        BeerIngredients(
          sectionName = stringResource(id = string.malt),
          ingredients = beer.ingredients.malt,
        )
        BeerIngredients(
          sectionName = stringResource(id = string.yeast),
          ingredients = listOf(beer.ingredients.yeast),
        )
      }
    }
  }
}

@Composable
private fun BeerIngredients(
  modifier: Modifier = Modifier,
  sectionName: String,
  ingredients: List<Ingredient>,
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.paddings.small)
  ) {
    Text(
      text = sectionName,
      style = MaterialTheme.typography.titleMedium
    )
    ingredients.forEach { ingredient ->
      BeerIngredient(
        modifier = Modifier
          .fillMaxWidth()
          .padding(start = MaterialTheme.paddings.small),
        ingredient = ingredient
      )
    }
  }
}

@Composable
private fun BeerIngredient(
  modifier: Modifier = Modifier,
  ingredient: Ingredient
) {
  Column(
    modifier = modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.paddings.small)
  ) {
    Text(
      text = ingredient.name,
      style = MaterialTheme.typography.labelLarge
    )
    ingredient.amount?.let { amount ->
      Text(
        text = stringResource(id = string.amount, amount.toString()),
        style = MaterialTheme.typography.labelSmall
      )
    }
  }
}

@ThemePreview
@Composable
private fun PreviewBeerDetailsScreen() {
  BeersTheme {
    BeerDetailsScreen(
      beerName = "Fake Lager",
      uiState = Success(
        data = Beer(
          id = 1,
          name = "Fake Lager",
          tagline = "A Real Bitter Experience.",
          description = "A light, crisp and bitter IPA brewed with English and American hops. " +
            "A small batch brewed only once.",
          imageUrl = "https://images.punkapi.com/v2/keg.png",
          ingredients = Ingredients(
            malt = listOf(
              Ingredient(
                name = "Maris Otter Extra Pale",
                amount = Amount(
                  value = "3.3",
                  unit = "kilograms"
                )
              )
            ),
            hops = listOf(
              Ingredient(
                name = "Fuggles",
                amount = Amount(
                  value = "25",
                  unit = "grams"
                )
              )
            ),
            yeast = Ingredient(
              name = "Wyeast 1056 - American Ale",
            )
          )
        )
      ),
      viewEvent = {},
      navigateUp = {}
    )
  }
}