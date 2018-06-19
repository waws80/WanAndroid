package com.thanatos.wanandroid

import android.os.Bundle
import com.thanatos.baselibrary.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import pw.androidthanatos.annotation.Path

@Path("app_main")
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testView.a()

    }


    override fun onResume() {
        super.onResume()

        main_toolbar.setNavClickListener { onBackPressed() }

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
