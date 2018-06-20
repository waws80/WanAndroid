package com.thanatos.baselibrary.mvp;

/**
 *  功能描述: presenter的生命周期函数
 *  @className: HolderCallBack
 *  @author: thanatos
 *  @createTime: 2018/6/20
 *  @updateTime: 2018/6/20 15:11
 */
public interface PresenterLifeCallBack<V extends BaseView>{

    /**
     * 绑定与之对应的view{@link BaseView}
     * @param view
     */
    void attach(V view);

    /**
     * 当前presenter已创建{@link BasePresenter}
     */
    void onCreate();

    /**
     * 当前presenter已挂起{@link BasePresenter}
     */
    void onPause();

    /**
     * 当前presenter已销毁{@link BasePresenter}
     */
    void onDestroy();

}
