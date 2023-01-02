package com.selsela.takeefapp.navigation

import androidx.navigation.NavController

object Destinations {
    const val SPLASH_SCREEN = "splash"
    const val INTRO_SCREEN = "intro"
}

class NavigationActions(private val navController: NavController){
    fun navigateToIntro(){
        navController.navigate(Destinations.INTRO_SCREEN)
    }
}