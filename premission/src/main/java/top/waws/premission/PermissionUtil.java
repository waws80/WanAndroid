package top.waws.premission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 权限申请封装类
 * Created on 2017/11/2.
 * @author liuxiongfei
 */
@SuppressWarnings("All")
public final class PermissionUtil {

    /**
     * 是否开启了调试模式
     */
    private static boolean DEBUG = false;

    public static final int OK = 100;

    public static final int ERROR = 101;

    private static final int REQUEST_CODE = 0x100;

    /**
     * 权限全局申请拦截器
     */
    private static IPermissionIntecepter mIntecepter;

    private List<String> mPermissions = new ArrayList<>();

    private WeakReference<Activity> mActivityWRF;
    private boolean mustAgree = false; //是否必须统一


    private PermissionUtil(){
    }

    /**
     * 内部静态类专门获取Permission实类对象
     */
    private static final class Builder{
        private static final PermissionUtil PERMISSION = new PermissionUtil();
    }

    /**
     * 不开启调试模式的初始化
     * @return Permission
     */
    public static PermissionUtil getInstance(){
        return getInstance(false);
    }

    /**
     * 开启调试模式的初始化
     * @param debug 是否开启调试模式
     * @return Permission
     */
    public static PermissionUtil getInstance(@NonNull boolean debug) {
        DEBUG = debug;
        Logger.init(DEBUG);
        return Builder.PERMISSION;
    }

    /**
     * 设置请求的权限
     * @param premission 权限
     * @return Permission
     */
    public PermissionUtil request(@NonNull List<String> premissions) {
        this.mPermissions.clear();
        this.mPermissions.addAll(premissions);
        return this;
    }

    /**
     * 构建上下文
     * @param activity
     * @return
     */
    public PermissionUtil build(Object activity){
        if (activity instanceof Activity){
            this.mActivityWRF = new WeakReference<>((Activity)activity);
        }
        return this;
    }


    public PermissionUtil mustAgree(){
        this.mustAgree = true;
        return this;
    }


    /**
     * 添加权限申请拦截器
     * @param intecepter
     * @return
     */
    public static void  setIntecepter(IPermissionIntecepter intecepter){
        mIntecepter = intecepter;
    }


    /**
     * 执行请求权限并获取回调
     * @param activity Activity
     */
    public void execute(@NonNull PermissionCallBack callBack){
        if (this.mActivityWRF == null || this.mActivityWRF.get() == null){
            Logger.d("申请权限的目标栈为空");
            return;
        }
        new Call(mPermissions, REQUEST_CODE, this.mActivityWRF.get(), callBack, mIntecepter, mustAgree);
    }


}
