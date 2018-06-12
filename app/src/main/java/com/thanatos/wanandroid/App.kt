package com.thanatos.wanandroid

import android.app.Application

import pw.androidthanatos.annotation.Module
import pw.androidthanatos.annotation.Modules
import pw.androidthanatos.router.Router

@Modules("app")
@Module("app")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Router.init(this)
        Router.debug(true)
    }
}
