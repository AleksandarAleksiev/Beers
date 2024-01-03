package com.aaleksiev.core.ui.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.aaleksiev.core.designsystem.ui.theme.BeersTheme
import com.aaleksiev.core.designsystem.ui.theme.paddings
import com.aaleksiev.core.ui.R.string
import com.aaleksiev.core.ui.preview.ThemePreview

@Composable
fun RetryableError(
  modifier: Modifier = Modifier,
  title: String = stringResource(string.error_generic_title),
  subtitle: String = stringResource(string.error_generic_subtitle),
  contentCardPadding: PaddingValues = PaddingValues(all = MaterialTheme.paddings.large),
  retry: () -> Unit
) {
  Column(
    modifier,
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    GenericError(title = title, subtitle = subtitle, contentCardPadding = contentCardPadding)
    TextButton(onClick = { retry() },
    ) {
      Text(text = stringResource(id = string.retry))
    }
  }
}

@Composable
fun GenericError(
  modifier: Modifier = Modifier,
  title: String,
  subtitle: String,
  contentCardPadding: PaddingValues = PaddingValues(all = MaterialTheme.paddings.large),
) {
  ElevatedCard(
    modifier = modifier.padding(contentCardPadding),
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = MaterialTheme.paddings.large),
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center
    ) {
      Icon(
        imageVector = Icons.Default.ErrorOutline,
        tint = MaterialTheme.colorScheme.error,
        contentDescription = null
      )
      Spacer(Modifier.padding(top = MaterialTheme.paddings.small))
      if (title.isNotEmpty()) {
        Text(
          modifier = Modifier.padding(horizontal = MaterialTheme.paddings.large),
          text = title,
          style = MaterialTheme.typography.bodyLarge,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center,
          color = MaterialTheme.colorScheme.onBackground
        )
      }
      Text(
        modifier = Modifier.padding(horizontal = MaterialTheme.paddings.large),
        text = subtitle,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurface,
        textAlign = TextAlign.Center,
      )
    }
  }
}

@ThemePreview
@Composable
private fun PreviewRetryableError() {
  BeersTheme {
    RetryableError{}
  }
}
