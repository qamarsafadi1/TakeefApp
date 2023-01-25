package com.selsela.takeefapp.navigation

import androidx.navigation.NavController
import com.selsela.takeefapp.data.config.model.Case
import com.selsela.takeefapp.navigation.Navigation.navigateWithClearBackStack
import com.selsela.takeefapp.utils.Constants.NEW_ORDER

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
    const val ORDERS_SCREEN = "orders/"
    const val ORDERS_SCREEN_ARGS = "orders/{case}"
    const val ORDER_ROUTE_SCREEN = "order_route_screen"
    const val ORDER_DETAILS = "order_details/"
    const val ORDER_DETAILS_ARGS = "order_details/{id}"
    const val SPECIAL_ORDERS = "special_orders"
    const val SPECIAL_ORDERS_ARGS = "special_orders_details/{id}"
    const val SPECIAL_ORDERS_DETAILS = "special_orders_details/"
    const val NOTIFICATION_SCREEN = "notification_screen"
    const val ABOUT_APP_SCREEN = "about_app_screen"
    const val TERMS = "terms"
    const val TECHNICAL_SUPPORT = "technical_support"
    const val PROFILE_SCREEN = "profile_screen"
    const val WALLET_SCREEN = "wallet_screen"
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

    fun navigateToLogin() {
        navController.navigate(Destinations.LOGIN_SCREEN)
    }

    fun navigateToVerify() {
        navController.navigate(Destinations.VERIFY_SCREEN)
    }

    fun navigateToAddress() {
        navController.navigate(Destinations.ADDRESS_SCREEN){
            popUpTo(Destinations.HOME_SCREEN ) {
                inclusive = false
            }
        }
    }

    fun navigateToSearchAddress(query: String?) {
        val queryResult = query ?: "none"
        navController.navigate("${Destinations.SEARCH_ADDRESS_SCREEN}${queryResult}")
    }

    fun navigateToReviewOrder() {
        navController.navigate(Destinations.REVIEW_ORDER)
    }

    fun navigateToSuccess() {
        navController.navigate(Destinations.SUCCESS)
    }

    fun navigateToSpecialOrder() {
        navController.navigate(Destinations.SPECIAL_ORDER)
    }

    fun navigateToMyAccount() {
        navController.navigate(Destinations.MY_ACCOUNT)
    }

    fun navigateToOrders(case: Int) {
        navController.navigate("${Destinations.ORDERS_SCREEN}${case}"){
        }
    }
    fun navigateToOrdersWithoutBackStack() {
        navController.navigate("${Destinations.ORDERS_SCREEN}${NEW_ORDER}"){
            popUpTo(Destinations.HOME_SCREEN ) {
                inclusive = false
            }
        }
    }

    fun navigateToOrderRoute() {
        navController.navigate(Destinations.ORDER_ROUTE_SCREEN)
    }

    fun navigateToOrderDetails(id: Int) {
        navController.navigate("${Destinations.ORDER_DETAILS}${id}")
    }

    fun navigateToSpecialOrders() {
        navController.navigate(Destinations.SPECIAL_ORDERS)
    }

    fun navigateToSpecialOrderDetails(id: Int) {
        navController.navigate("${Destinations.SPECIAL_ORDERS_DETAILS}${id}")
    }

    fun navigateToNotification() {
        navController.navigate(Destinations.NOTIFICATION_SCREEN)
    }

    fun navigateToAboutApp() {
        navController.navigate(Destinations.ABOUT_APP_SCREEN)
    }

    fun navigateToTermsScreen() {
        navController.navigate(Destinations.TERMS)
    }

    fun navigateToSupport() {
        navController.navigate(Destinations.TECHNICAL_SUPPORT)
    }

    fun navigateToProfile() {
        navController.navigate(Destinations.PROFILE_SCREEN)
    }

    fun navigateToWallet() {
        navController.navigate(Destinations.WALLET_SCREEN)
    }
}