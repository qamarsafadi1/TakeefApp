package com.selsela.takeefapp.navigation

import androidx.navigation.NavController
import com.selsela.takeefapp.utils.Extensions.Companion.navigateWithClearBackStack

object Destinations {
    const val SPLASH_SCREEN = "splash"
    const val INTRO_SCREEN = "intro"
    const val HOME_SCREEN = "home"
    const val LOGIN_SCREEN = "login"
    const val VERIFY_SCREEN = "verify"
    const val ADDRESS_SCREEN = "address"
    const val SEARCH_ADDRESS_SCREEN = "address/"
    const val SEARCH_ADDRESS_SCREEN_WITH_ARGUMENT = "address/{query}"
    const val REVIEW_ORDER = "review_order"
    const val SUCCESS = "success"
    const val ERROR = "error"
    const val SPECIAL_ORDER = "special_order"
    const val MY_ACCOUNT = "my_account"
    const val ORDERS_SCREEN = "orders"
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
    fun navigateToLogin(){
        navController.navigate(Destinations.LOGIN_SCREEN)
    }
    fun navigateToVerify(){
        navController.navigate(Destinations.VERIFY_SCREEN)
    }
    fun navigateToAddress(){
        navController.navigate(Destinations.ADDRESS_SCREEN)
    }
    fun navigateToSearchAddress(query: String?){
        val queryResult = query ?: "none"
        navController.navigate("${Destinations.SEARCH_ADDRESS_SCREEN}${queryResult}")
    }

    fun navigateToReviewOrder(){
        navController.navigate(Destinations.REVIEW_ORDER)
    }
    fun navigateToSuccess(){
        navController.navigate(Destinations.SUCCESS)
    }
    fun navigateToSpecialOrder(){
        navController.navigate(Destinations.SPECIAL_ORDER)
    }
    fun navigateToMyAccount(){
        navController.navigate(Destinations.MY_ACCOUNT)
    }
    fun navigateToOrders(){
        navController.navigate(Destinations.ORDERS_SCREEN)
    }

}