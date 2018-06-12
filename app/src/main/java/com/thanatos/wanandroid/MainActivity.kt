package com.thanatos.wanandroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import pw.androidthanatos.annotation.Path

@Path("app_main")
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
