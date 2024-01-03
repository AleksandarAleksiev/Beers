package com.aaleksiev.beer.ui

sealed interface BeersViewEvent {
  data object Reload : BeersViewEvent
}
