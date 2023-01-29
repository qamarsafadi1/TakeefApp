package com.selsela.takeefapp.data.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log


open class NotificationReceiver : BroadcastReceiver {
    var onNotificationReceived: onNotificationReceived? = null

    constructor() {}
    constructor(onNotificationReceived: onNotificationReceived?) {
        this.onNotificationReceived = onNotificationReceived
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.extras != null) {
            val extras = intent.extras
            for (key in extras!!.keySet()) {
                Log.d("<==> ", key + " : " + extras[key])
            }
        }
        Log.d("<==>! ", intent.getStringExtra("action")!!)
        onNotificationReceived!!.onReceived(intent.getStringExtra("action"))
    }
}
