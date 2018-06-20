package com.thanatos.baselibrary.sputil

import android.content.Context
import com.thanatos.baselibrary.base.BaseApplication

/**
 *  功能描述: SpUtil 工具类
 *  @className: SpUtil
 *  @author: thanatos
 *  @createTime: 2018/6/20
 *  @updateTime: 2018/6/20 17:36
 */
object SpUtil{

    /**
     * 创建sp实体类对象
     */
    private val sp = BaseApplication.getContext()
            .getSharedPreferences("WanAndroid",Context.MODE_PRIVATE)


    /**
     * 获取数据
     */
    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    fun <T> get(key: String, default: T):T = with(sp){
        when(default){
            default is Int -> getInt(key,default as Int) as T
            default is Float -> getFloat(key, default as Float) as T
            default is Long -> getLong(key, default as Long) as T
            default is Boolean -> getBoolean(key, default as Boolean) as T
            default is String -> getString(key, default as String) as T
            default is String -> getStringSet(key, default as MutableSet<String>) as T
            else -> {
                throw IllegalArgumentException("不支持的类型")
            }
        }
    }


    /**
     * 添加数据
     */
    fun <T> put(key: String, value: T){
        val edit = sp.edit()
        when(value){
            value is Int -> edit.putInt(key,value as Int)
            value is Float -> edit.putFloat(key, value as Float)
            value is Long -> edit.putLong(key, value as Long)
            value is Boolean -> edit.putBoolean(key, value as Boolean)
            value is String -> edit.putString(key, value as String)
            value is String -> edit.putStringSet(key, value as MutableSet<String>)
            else -> {
                throw IllegalArgumentException("不支持的类型")
            }
        }
        edit.apply()
    }

    /**
     * 移除某个值
     */
    fun remove(key: String){
        sp.edit().remove(key).apply()
    }

    /**
     * 清空sp
     */
    fun clear(){
        sp.edit().clear().apply()
    }

}
