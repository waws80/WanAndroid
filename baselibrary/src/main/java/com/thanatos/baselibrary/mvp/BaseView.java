package com.thanatos.baselibrary.mvp;

import android.support.annotation.NonNull;
import android.support.transition.Slide;
import android.view.Gravity;

public interface BaseView {

    void showProgress(@NonNull String text);

    void hideProgress();

    void showInfo(@NonNull String text, @Slide.GravityFlag int gravity);
}
