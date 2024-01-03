package com.aaleksiev.core.ui.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import com.aaleksiev.core.designsystem.ui.theme.BeersTheme
import com.aaleksiev.core.ui.preview.ThemePreview

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SimpleTopAppBar(
  title: String,
  navigateUp: () -> Unit = {},
  navigationIcon: ImageVector? = Icons.Default.ArrowBack,
  colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(
    containerColor =  MaterialTheme.colorScheme.background,
    navigationIconContentColor = contentColorFor(MaterialTheme.colorScheme.background)
  ),
  scrollBehavior: TopAppBarScrollBehavior? = null
) {
  TopAppBar(
    title = {
      Text(
        text = title,
        overflow = TextOverflow.Ellipsis,
      )
    },
    navigationIcon = {
      if (navigationIcon != null) {
        IconButton(onClick = navigateUp) {
          Icon(
            imageVector = navigationIcon,
            contentDescription = null,
          )
        }
      }
    },
    scrollBehavior = scrollBehavior,
    colors = colors
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@ThemePreview
private fun PreviewSmallTopAppBar() {
  BeersTheme {
    SimpleTopAppBar(
      title = "Simple top app bar",
      navigateUp = { }
    )
  }
}