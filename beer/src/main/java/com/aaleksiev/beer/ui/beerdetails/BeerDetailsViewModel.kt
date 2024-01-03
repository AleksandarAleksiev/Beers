package com.aaleksiev.beer.ui.beerdetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaleksiev.beer.ui.BeersViewEvent
import com.aaleksiev.beer.ui.BeersViewEvent.Reload
import com.aaleksiev.beer.ui.navigation.BeersNavigation.BEER_ID_ARG
import com.aaleksiev.core.data.beer.BeerRepository
import com.aaleksiev.core.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
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
    .flatMapLatest { beerId -> beerRepository.beerDetails(id = beerId) }
    .mapLatest { result ->
      result.fold(
        onSuccess = { UiState.Success(data = it) },
        onFailure = { UiState.Error }
      )
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