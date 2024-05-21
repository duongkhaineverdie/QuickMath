package com.mathtech.quickmath.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mathtech.quickmath.domain.navigation.Destination
import com.mathtech.quickmath.domain.navigation.NavHostNoteApp
import com.mathtech.quickmath.domain.navigation.composable
import com.mathtech.quickmath.presentation.ui.home.HomeScreen
import com.mathtech.quickmath.presentation.ui.theme.QuickMathTheme
import com.mathtech.quickmath.presentation.ui.quickmathgame.QuickMathGameScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        // Configure the behavior of the hidden system bars.
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        // Add a listener to update the behavior of the toggle fullscreen button when
        // the system bars are hidden or revealed.
        window.decorView.setOnApplyWindowInsetsListener { view, windowInsets ->
            // You can hide the caption bar even when the other system bars are visible.
            // To account for this, explicitly check the visibility of navigationBars()
            // and statusBars() rather than checking the visibility of systemBars().
            windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
            view.onApplyWindowInsets(windowInsets)
        }

        setContent {
            val navController: NavHostController = rememberNavController()
            QuickMathTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetupNavigation(navController = navController)
                }
            }
        }
    }


}

@Composable
private fun SetupNavigation(navController: NavHostController) {
    NavHostNoteApp(navController = navController, startDestination = Destination.HomeScreen) {
        composable(Destination.HomeScreen) { HomeScreen(navController) }
        composable(Destination.QuickMathScreen) { QuickMathGameScreen(navController) }
    }
}