package com.selsela.takeefapp.navigation

import androidx.navigation.NavController
import com.selsela.takeefapp.utils.Extensions.Companion.navigateWithClearBackStack

object Destinations {
    const val SPLASH_SCREEN = "splash"
    const val INTRO_SCREEN = "intro"
    const val HOME_SCREEN = "home"
}

class NavigationActions(private val navController: NavController) {
    fun navigateToIntro() {
        navController.navigate(Destinations.INTRO_SCREEN) {
            navigateWithClearBackStack(navController)
        }
    }

    fun navigateToHome() {
        navController.navigate(Destinations.HOME_SCREEN) {
            navigateWithClearBackStack(navController)
        }
    }
}