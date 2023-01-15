package com.selsela.takeefapp.utils

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.selsela.takeefapp.R
import com.selsela.takeefapp.navigation.Destinations

object Navigation {
    fun NavController.bindToolbarTitle(currentRoute: NavBackStackEntry): String {
        val title = when (currentRoute.destination.route) {
            Destinations.HOME_SCREEN, Destinations.LOGIN_SCREEN,
            Destinations.VERIFY_SCREEN, Destinations.SUCCESS, Destinations.SPECIAL_ORDER -> ""

            Destinations.SEARCH_ADDRESS_SCREEN_WITH_ARGUMENT -> this.context.getString(R.string.chosse_address)
            Destinations.REVIEW_ORDER -> this.context.getString(R.string.review_order)
            Destinations.ORDERS_SCREEN -> this.context.getString(R.string.new_orders)
            Destinations.ORDER_ROUTE_SCREEN -> this.context.getString(R.string.order_route)
            Destinations.ORDER_DETAILS -> this.context.getString(R.string.order_details)
            Destinations.SPECIAL_ORDERS -> this.context.getString(R.string.special_order)
            else -> "Selsela"
        }
        return title
    }

    fun NavController.bindPadding(currentRoute: NavBackStackEntry): Int {
        val title = when (currentRoute.destination.route) {
            Destinations.HOME_SCREEN, Destinations.LOGIN_SCREEN,
            Destinations.VERIFY_SCREEN, Destinations.SUCCESS, Destinations.SPECIAL_ORDER, Destinations.MY_ACCOUNT,
            Destinations.ORDER_DETAILS -> 0

            else -> 60
        }
        return title
    }

    fun NavController.showingBackButton(currentRoute: NavBackStackEntry): Boolean {
        var showBackButton = when (currentRoute.destination.route) {
            Destinations.HOME_SCREEN, Destinations.SPLASH_SCREEN, Destinations.SUCCESS -> false
            else -> true
        }
        return showBackButton
    }

    fun NavOptionsBuilder.navigateWithClearBackStack(navController: NavController) {
        popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
            inclusive = true
        }
    }

}