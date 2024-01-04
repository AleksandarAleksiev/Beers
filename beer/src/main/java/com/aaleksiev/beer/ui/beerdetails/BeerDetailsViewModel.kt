package com.aaleksiev.beer.ui.beerdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaleksiev.beer.ui.BeersViewEvent
import com.aaleksiev.beer.ui.BeersViewEvent.Reload
import com.aaleksiev.beer.ui.navigation.BeersNavigation.BEER_ID_ARG
import com.aaleksiev.core.data.beer.BeerRepository
import com.aaleksiev.core.ui.UiState
import com.aaleksiev.core.ui.UiState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeerDetailsViewModel @Inject constructor(
  private val beerRepository: BeerRepository,
  savedStateHandle: SavedStateHandle,
) : ViewModel() {

  private val beersViewEventFlow = MutableSharedFlow<BeersViewEvent>()

  @OptIn(ExperimentalCoroutinesApi::class)
  val beerDetailsUiState = combine(
    beersViewEventFlow.onStart { emit(Reload) },
    savedStateHandle.getStateFlow(key = BEER_ID_ARG, -1L)
  ) { _, beerId -> beerId }
    .transformLatest { beerId ->
      emit(Loading)
      beerRepository
        .beerDetails(id = beerId)
        .map { result ->
          result.fold(
            onSuccess = { UiState.Success(data = it) },
            onFailure = { UiState.Error }
          )
        }
        .onEach { state -> emit((state)) }
        .collect()
    }
    .stateIn(
      scope = viewModelScope,
      started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
      initialValue = UiState.Loading
    )

  fun setEvent(beersViewEvent: BeersViewEvent) {
    viewModelScope.launch {
      beersViewEventFlow.emit(beersViewEvent)
    }
  }
}