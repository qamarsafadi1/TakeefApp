package com.selsela.takeefapp.data.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.selsela.takeefapp.MainActivity
import com.selsela.takeefapp.R
import com.selsela.takeefapp.utils.Constants.ORDER_ADDITIONAL_COST
import com.selsela.takeefapp.utils.Constants.ORDER_STATUS_CHANGED
import com.selsela.takeefapp.utils.Constants.VERIFY_CODE
import com.selsela.takeefapp.utils.Constants.WALLET_CHANGED
import com.selsela.takeefapp.utils.Extensions.Companion.log
import com.selsela.takeefapp.utils.LocalData
import org.json.JSONException
import org.json.JSONObject
import java.util.*


class Controller : FirebaseMessagingService() {
    var TAG = "Notification =>"
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        if (remoteMessage.data.isNotEmpty()) {
            try {
                val value = Gson().toJson(remoteMessage.data)
                val json = JSONObject(value)
                handleDataMessage(json, remoteMessage)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            sendNotification(
                remoteMessage.notification?.title ?: "",
                remoteMessage.notification?.body ?: "",
                "none"
            )
        }
    }


    private fun handleDataMessage(json: JSONObject, remoteMessage: RemoteMessage) {
        Log.d("no - push json: %s", json.toString())
        json.toString().log()
        try {
            if (json.has("action")) {
                when (json.getString("action")) {
                    VERIFY_CODE -> {
                        sendNotification(
                            remoteMessage.notification?.title ?: "",
                            remoteMessage.notification?.body ?: "",
                            "none"
                        )
                    }

                    ORDER_STATUS_CHANGED, ORDER_ADDITIONAL_COST -> {
                        val orderId = json.getString("order_id")
                        orderId.log("orderId")
                        if (json.getString("action") == ORDER_ADDITIONAL_COST) {
                            val localIntent = Intent(ORDER_ADDITIONAL_COST)
                            localIntent.putExtra(
                                "additional_cost",
                                json.getString("additional_cost")
                            )
                            localIntent.putExtra("orderId", orderId)
                            val manager = LocalBroadcastManager.getInstance(this)
                            manager.sendBroadcast(localIntent)
                        } else {
                            val localIntent = Intent(ORDER_STATUS_CHANGED)
                            val manager = LocalBroadcastManager.getInstance(this)
                            manager.sendBroadcast(localIntent)
                        }
                        sendNotification(
                            remoteMessage.notification?.title ?: "",
                            remoteMessage.notification?.body ?: "",
                            MainActivity::class.java.simpleName,
                            orderId
                        )
                    }

                    WALLET_CHANGED -> {
                        val localIntent = Intent(WALLET_CHANGED)
                        val manager = LocalBroadcastManager.getInstance(this)
                        manager.sendBroadcast(localIntent)
                        sendNotification(
                            remoteMessage.notification?.title ?: "",
                            remoteMessage.notification?.body ?: "",
                            MainActivity::class.java.simpleName,
                        )
                    }

                    else -> {
                        sendNotification(
                            remoteMessage.notification?.title ?: "",
                            remoteMessage.notification?.body ?: "",
                            MainActivity::class.java.simpleName,
                        )
                    }
                }
            } else {
                val localIntent = Intent("notification")
                val manager = LocalBroadcastManager.getInstance(this)
                manager.sendBroadcast(localIntent)
                sendNotification(
                    remoteMessage.notification?.title ?: "",
                    remoteMessage.notification?.body ?: "",
                    MainActivity::class.java.simpleName,
                )
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    private fun sendNotification(
        title: String,
        body: String,
        className: String,
        orderID: String? = null,
    ) {
        var contentIntent: PendingIntent? = null
        val intent = if (orderID.isNullOrEmpty().not()) {
            Intent(
                Intent.ACTION_VIEW,
                "https://airconditioner.com/id=${orderID}".toUri(),
                applicationContext,
                MainActivity::class.java
            )
        } else Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        contentIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            TaskStackBuilder.create(applicationContext).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
            }
        } else {
            TaskStackBuilder.create(applicationContext).run {
                addNextIntentWithParentStack(intent)
                getPendingIntent(
                    0,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
        }
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val channelId = getString(R.string.default_notification_channel_id)
        if (className != "none") {
            val notificationBuilder = NotificationCompat.Builder(
                this,
                channelId
            )
                .setSmallIcon(R.drawable.notifictionimage)
                .setColor(Color.WHITE)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(contentIntent)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel =
                    NotificationChannel(channelId, className, NotificationManager.IMPORTANCE_HIGH)
                channel.description = body
                notificationManager.createNotificationChannel(channel)
                notificationBuilder.setChannelId(channelId)
            }
            notificationManager.notify(
                Random().nextInt(),
                notificationBuilder.build()
            )
        } else {
            val notificationBuilder = NotificationCompat.Builder(
                this,
                channelId
            )
                .setSmallIcon(R.drawable.notifictionimage)
                .setColor(Color.WHITE)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel =
                    NotificationChannel(channelId, className, NotificationManager.IMPORTANCE_HIGH)
                channel.description = body
                notificationManager.createNotificationChannel(channel)
                notificationBuilder.setChannelId(channelId)
            }
            notificationManager.notify(
                Random().nextInt(),
                notificationBuilder.build()
            )
        }


    }

    override fun onNewToken(_token: String) {
        super.onNewToken(_token)
        Log.e(TAG, "FCM TOKEN => $_token")
        Log.e("FCM TOKEN => ", _token)
        LocalData.fcmToken = _token
    }


}