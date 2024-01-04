package com.aaleksiev.beer.ui.beer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaleksiev.beer.ui.BeersViewEvent
import com.aaleksiev.beer.ui.BeersViewEvent.Reload
import com.aaleksiev.core.data.beer.BeerRepository
import com.aaleksiev.core.ui.UiState
import com.aaleksiev.core.ui.UiState.Loading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transformLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeersViewModel @Inject constructor(
  private val beersRepository: BeerRepository,
) : ViewModel() {

  private val beersViewEventFlow = MutableSharedFlow<BeersViewEvent>()

  @OptIn(ExperimentalCoroutinesApi::class)
  val beersUiState = beersViewEventFlow
    .onStart { emit(Reload) }
    .filter { it is Reload }
    .transformLatest {
      emit(Loading)
      beersRepository.beers()
        .map { result ->
          result.fold(
            onSuccess = { UiState.Success(data = it) },
            onFailure = { UiState.Error }
          )
        }
        .onEach { state -> emit(state) }
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