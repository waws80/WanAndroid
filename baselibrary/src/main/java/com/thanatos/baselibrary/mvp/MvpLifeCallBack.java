package com.thanatos.baselibrary.mvp;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.LinkedList;

/**
 *  功能描述: 程序各个活动容器的生命周期回调
 *  @className: MvpLifeCallBack
 *  @author: thanatos
 *  @createTime: 2018/6/20
 *  @updateTime: 2018/6/20 14:56
 */
class MvpLifeCallBack {

    private static LinkedList<Activity> sActivitys = new LinkedList<>();

    /**
     * 活动生命周期回调
     */
    private static Application.ActivityLifecycleCallbacks lifecycleCallbacks = new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            //若当前activity已经添加入provider则进行生命周期绑定
            if (PresenterProviders.sPresenterTables.containsKey(activity.getClass().getSimpleName())){
                PresenterLifeCallBack callBack = PresenterProviders.sPresenterTables.get(activity.getClass().getSimpleName()).getHolderCallBack();
                if (callBack != null){
                    //noinspection unchecked
                    callBack.attach((BaseView) activity);
                    callBack.onStart();
                }
            }
            sActivitys.addFirst(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {

        }

        @Override
        public void onActivityResumed(Activity activity) {
            //若当前activity已经添加入provider则进行生命周期绑定
            if (PresenterProviders.sPresenterTables.containsKey(activity.getClass().getSimpleName())){
                PresenterLifeCallBack callBack = PresenterProviders.sPresenterTables.get(activity.getClass().getSimpleName()).getHolderCallBack();
                if (callBack != null && !callBack.visible()){
                    callBack.onStart();
                }
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            //若当前activity在provider中，则挂起当前 presenter
            if (PresenterProviders.sPresenterTables.containsKey(activity.getClass().getSimpleName())){
                PresenterLifeCallBack callBack = PresenterProviders.sPresenterTables.get(activity.getClass().getSimpleName()).getHolderCallBack();
                if (callBack != null){
                    callBack.onPause();
                }
            }
        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            //若当前activity在provider中，则停止当前presenter的活动
            if (PresenterProviders.sPresenterTables.containsKey(activity.getClass().getSimpleName())){
                PresenterLifeCallBack callBack = PresenterProviders.sPresenterTables.get(activity.getClass().getSimpleName()).getHolderCallBack();
                if (callBack != null){
                    callBack.onDestroy();
                }
            }
            sActivitys.remove(activity);
        }
    };

    /**
     * 注册程序的生命周期监听
     * @param application
     */
    static void register(Application application){
        application.registerActivityLifecycleCallbacks(lifecycleCallbacks);
    }

    /**
     * 销毁程序的生命周期监听
     * @param application
     */
    static void unregister(Application application){
        application.unregisterActivityLifecycleCallbacks(lifecycleCallbacks);
        PresenterProviders.sPresenterTables.clear();
    }


}
