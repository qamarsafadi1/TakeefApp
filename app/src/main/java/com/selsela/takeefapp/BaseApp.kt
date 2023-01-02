package com.selsela.takeefapp

import android.app.Application
import com.orhanobut.hawk.Hawk
import com.selsela.takeefapp.utils.LocalData
import com.yariksoffice.lingver.Lingver

import java.util.*

class BaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Hawk.init(this).build()
        Lingver.init(this, LocalData.appLocal)
        Lingver.getInstance().setLocale(this, LocalData.appLocal)
    }
}