package com.thanatos.baselibrary.mvp

import android.support.design.widget.Snackbar
import android.support.transition.Slide
import android.view.Gravity

interface BaseView {

    fun showProgress(text: String)

    fun hideProgress()

    fun showInfo(text: String, @Slide.GravityFlag gravity: Int = Gravity.BOTTOM)

    fun showSnackBar(msg: String, duration: Int = Snackbar.LENGTH_SHORT)
}
