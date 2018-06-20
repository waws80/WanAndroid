package com.thanatos.baselibrary.mvp;

import android.util.Log;

/**
 *  功能描述: presenter基类 并且赋予了和对应 view 相关联的生命周期
 *  @className: BasePresenter
 *  @author: thanatos
 *  @createTime: 2018/6/20
 *  @updateTime: 2018/6/20 14:42
 */
public abstract class BasePresenter<V extends BaseView> implements PresenterLifeCallBack{

    private static final String TAG = "BasePresenter";

    /**
     * 与之相关联的view{@link BaseView}
     */
    private V mView;

    /**
     * 是否对用户可见，不可见的时候可能与之对应的view正在创建或者正在销毁
     */
    private boolean isShowAndCanLoadData = false;


    @Override
    public void attach(BaseView view) {
        this.mView = (V) view;
        Log.d(TAG, "attach: ");
    }

    @Override
    public void onCreate(){
        this.isShowAndCanLoadData = true;
        Log.d(TAG, "onCreate: ");
    }

    /**
     * 当前presenter是否对用户可见
     * @return true: 可见   false： 不可见
     */
    public boolean visible(){
        return this.isShowAndCanLoadData;
    }


    @Override
    public void onPause(){
        this.isShowAndCanLoadData = false;
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onDestroy(){
        this.isShowAndCanLoadData = false;
        if (this.mView != null){
            this.mView = null;
        }
        Log.d(TAG, "onDestroy: ");
    }
}
