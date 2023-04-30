package com.selsela.takeefapp

import android.app.Application
import com.google.firebase.FirebaseApp
import com.orhanobut.hawk.Hawk
import com.selsela.takeefapp.utils.LocalData
import com.selsela.takeefapp.utils.LocalUtils.setLocale
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp

import java.util.*

@HiltAndroidApp
class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        Hawk.init(this).build()
        if (LocalData.appLocal.isEmpty())
            setLocale("ar")
        else
            setLocale(LocalData.appLocal)

    }
}