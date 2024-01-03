package com.aaleksiev.core.designsystem.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object Paddings {
  val small: Dp = 8.dp
  val medium: Dp = 12.dp
  val large: Dp = 16.dp
}

val MaterialTheme.paddings
  get() = Paddings