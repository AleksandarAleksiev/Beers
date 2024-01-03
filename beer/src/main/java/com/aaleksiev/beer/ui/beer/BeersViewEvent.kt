package com.aaleksiev.beer.ui.beer

sealed interface BeersViewEvent {
  data object Reload : BeersViewEvent
}
