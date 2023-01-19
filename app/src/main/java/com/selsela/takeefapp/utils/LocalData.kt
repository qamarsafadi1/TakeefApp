package com.selsela.takeefapp.utils

import com.orhanobut.hawk.Hawk
import com.selsela.takeefapp.data.auth.model.auth.User
import com.selsela.takeefapp.data.config.model.AcType
import com.selsela.takeefapp.data.config.model.Configurations
import com.selsela.takeefapp.data.config.model.Service


open class LocalData {
    companion object {
        var appLocal: String = Hawk.get("appLocal", "ar")
            set(value) {
                field = value
                Hawk.put("appLocal", value)
            }
        var fcmToken: String = Hawk.get("fcmToken", "")
            set(value) {
                field = value
                Hawk.put("fcmToken", value)
            }
        var accessToken: String = Hawk.get("token", "")
            set(value) {
                field = value
                Hawk.put("token", value)
            }
        var user: User? = Hawk.get("user")
            set(value) {
                field = value
                Hawk.put("user", value)
            }

        var firstLaunch: Boolean = Hawk.get("firstLaunch", true)
            set(value) {
                field = value
                Hawk.put("firstLaunch", value)
            }

        var configurations: Configurations? = Hawk.get("configurations")
            set(value) {
                field = value
                Hawk.put("configurations", value)
            }
        var acTypes: List<AcType>? = Hawk.get("acTypes")
            set(value) {
                field = value
                Hawk.put("acTypes", value)
            }

        var services: List<Service>? = Hawk.get("services")
            set(value) {
                field = value
                Hawk.put("services", value)
            }
    }

}