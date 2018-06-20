package com.thanatos.baselibrary.mvp;

import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
/**
 *  功能描述:
 *  @className: PresenterProvider
 *  @author: thanatos
 *  @createTime: 2018/6/20
 *  @updateTime: 2018/6/20 15:07
 */
public class PresenterProvider {

    private static final String sFragmentTag = "com.thanatos.baselibrary.mvp.holderFragment";

    /**
     * presenter{@link BasePresenter}
     */
    private BasePresenter presenter;

    /**
     * 获取当前活动页面对应的presenter
     * @param clazz
     * @param <P>
     * @return
     */
    @MainThread
    @Nullable
    public <P extends BasePresenter> P get(@NonNull Class<P> clazz){
        try {
            P presenter = clazz.newInstance();
            if (null == this.presenter){
                this.presenter = presenter;
            }
            return presenter;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生命周期感知回调函数 ，提供给全局的程序生命周期回调使用
     * @return
     */
    @Nullable
    PresenterLifeCallBack getHolderCallBack(){
        return this.presenter;
    }

}
