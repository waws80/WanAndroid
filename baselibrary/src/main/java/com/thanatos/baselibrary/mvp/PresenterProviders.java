package com.thanatos.baselibrary.mvp;

import android.app.Application;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import java.util.concurrent.ConcurrentHashMap;

/**
 *  功能描述: 获取 presenter的容器类
 *  @className: PresenterProviders
 *  @author: thanatos
 *  @createTime: 2018/6/20
 *  @updateTime: 2018/6/20 14:56
 */
public class PresenterProviders {

    private static final String sFragmentTag = "com.thanatos.baselibrary.mvp.holderFragment";

    static final ConcurrentHashMap<String, PresenterProvider>
        sPresenterTables = new ConcurrentHashMap<>();

    /**
     * 初始化程序生命周期
     * @param application
     */
    @MainThread
    public static void init(@NonNull Application application){
        MvpLifeCallBack.register(application);
    }

    /**
     * 获取activity对应的 PresenterProvider{@link PresenterProvider}
     * @param activity
     * @return
     */
    @MainThread
    public static PresenterProvider ofActivity(@NonNull FragmentActivity activity){
        String key = activity.getClass().getSimpleName();
        sPresenterTables.put(activity.getClass().getSimpleName(),
                buildProvider(activity));
        return sPresenterTables.get(key);
    }

    /**
     * 获取activity对应的 PresenterProvider{@link PresenterProvider}
     * @param fragment
     * @return
     */
    @MainThread
    public static PresenterProvider ofFragment(@NonNull Fragment fragment){
        if (fragment.getActivity() == null){
            throw new NullPointerException("activity is destroy for window");
        }
        String key = fragment.getActivity().getClass().getSimpleName();
        sPresenterTables.put(fragment.getActivity().getClass().getSimpleName(),
                buildProvider(fragment.getActivity()));
        return sPresenterTables.get(key);
    }

    /**
     * 创建一个PresenterProvider同时校验目标activity是否已经destroy
     * @param activity
     * @return
     */
    @MainThread
    private static PresenterProvider buildProvider(FragmentActivity activity) {
        if (activity == null){
            throw new NullPointerException("activity is destroy for window");
        }
        return new PresenterProvider();
    }
}
