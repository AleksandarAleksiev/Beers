package com.aaleksiev.beer.ui.beer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aaleksiev.beer.ui.beer.BeersViewEvent.Reload
import com.aaleksiev.core.data.beer.BeerRepository
import com.aaleksiev.core.ui.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
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
    .flatMapLatest { beersRepository.beers() }
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