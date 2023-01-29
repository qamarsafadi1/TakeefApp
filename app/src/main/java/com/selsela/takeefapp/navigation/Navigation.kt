package com.selsela.takeefapp.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import com.selsela.takeefapp.R
import com.selsela.takeefapp.navigation.Destinations
import com.selsela.takeefapp.utils.Constants.FINISHED
import com.selsela.takeefapp.utils.Constants.NEW_ORDER
import com.selsela.takeefapp.utils.Constants.UPCOMING_ORDERS
import com.selsela.takeefapp.utils.Extensions.Companion.log

object Navigation {
    fun NavController.bindToolbarTitle(currentRoute: NavBackStackEntry): String {
        val title = when (currentRoute.destination.route) {
            Destinations.HOME_SCREEN, Destinations.LOGIN_SCREEN,
            Destinations.VERIFY_SCREEN, Destinations.SUCCESS,
            Destinations.SPECIAL_ORDER, Destinations.ABOUT_APP_SCREEN, Destinations.TERMS -> ""

            Destinations.SEARCH_ADDRESS_SCREEN_WITH_ARGUMENT -> this.context.getString(R.string.chosse_address)
            Destinations.REVIEW_ORDER -> this.context.getString(R.string.review_order)

            Destinations.ORDER_ROUTE_SCREEN -> this.context.getString(R.string.order_route)
            Destinations.ORDER_DETAILS, Destinations.ORDER_DETAILS_ARGS -> this.context.getString(R.string.order_details)
            Destinations.SPECIAL_ORDERS -> this.context.getString(R.string.special_order)
            Destinations.SPECIAL_ORDERS_DETAILS, Destinations.SPECIAL_ORDERS_ARGS -> this.context.getString(
                R.string.special_order_detail
            )

            Destinations.NOTIFICATION_SCREEN -> this.context.getString(R.string.notification)
            Destinations.TECHNICAL_SUPPORT -> this.context.getString(R.string.tech_support)
            Destinations.PROFILE_SCREEN -> this.context.getString(R.string.profile)
            Destinations.WALLET_SCREEN -> this.context.getString(R.string.wallet)
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
        popUpTo(navController.graph.id ?: return) {
            inclusive = true
        }
    }

}