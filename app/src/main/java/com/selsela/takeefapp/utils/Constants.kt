package com.selsela.takeefapp.utils

object Constants {
    const val LEFT = 0
    const val RIGHT = 1
    const val REJECT = 0
    const val ACCEPT = 1

    // Services Type Constants

    const val MAINTENANCE = 1
    const val CLEANING = 2
    const val INSTALLATION = 3
    const val NOT_VERIFIED = "not_verified"

    const val LOG_OUT = 1
    const val LOG_IN = 2

    // Wallet Constants
    const val CREDIT = "credit"
    const val DEBIT = "debit"

    // Support Constants
    const val REPLY = "reply"
    const val CONTACT = "contact"

    // Order Constants
    const val NEW_ORDER = 1
    const val UPCOMING_ORDERS = 2
    const val ON_WAY = 3
    const val RECEIVED = 1
    const val FINISHED = 5
    const val WALLET = 5
    const val COD = 4

    // Notification actions

    const val VERIFY_CODE = "sms_message"
    const val ORDER_STATUS_CHANGED = "order_status_changed"
    const val WALLET_CHANGED = "wallet_changed"
    const val ORDER_ADDITIONAL_COST = "order_need_additional_cost"

    const val PLACEHOLDER = "http://airconditionars.selselatech.com/uploads/blank.png"
}