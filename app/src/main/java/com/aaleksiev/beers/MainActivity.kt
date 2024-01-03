package com.aaleksiev.beers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.aaleksiev.beer.ui.navigation.BeersNavigation
import com.aaleksiev.beer.ui.navigation.BeersNavigation.beersNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
          com.aaleksiev.core.designsystem.ui.theme.BeersTheme {
            // A surface container using the 'background' color from the theme
            Surface(
              modifier = Modifier.fillMaxSize(),
              color = MaterialTheme.colorScheme.background
            ) {
              val navController = rememberNavController()
              NavHost(
                navController = navController,
                startDestination = BeersNavigation.BEERS
              ) {
                beersNavGraph(navController)
              }
            }
          }
        }
    }
}
