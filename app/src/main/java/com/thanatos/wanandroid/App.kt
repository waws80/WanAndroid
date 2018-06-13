package com.thanatos.wanandroid

import com.thanatos.baselibrary.base.BaseApplication

import pw.androidthanatos.annotation.Module
import pw.androidthanatos.annotation.Modules

@Modules("app","mine")
@Module("app")
class App : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
    }
}
