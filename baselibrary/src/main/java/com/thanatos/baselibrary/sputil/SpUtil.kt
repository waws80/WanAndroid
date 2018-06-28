package com.thanatos.baselibrary.sputil

import android.content.Context
import com.thanatos.baselibrary.base.BaseApplication
import kotlin.reflect.KClass

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
    private val sp = BaseApplication.context
            .getSharedPreferences("WanAndroid",Context.MODE_PRIVATE)


    /**
     * 获取数据
     */
    @Suppress("IMPLICIT_CAST_TO_ANY", "UNCHECKED_CAST")
    fun <T> get(key: String, default: T):T = with(sp){
        val a = default is String
        when(default){
            is Int -> getInt(key,default as Int) as T
            is Float -> getFloat(key, default as Float) as T
            is Long -> getLong(key, default as Long) as T
            is Boolean -> getBoolean(key, default as Boolean) as T
            is String -> getString(key, default as String) as T
            is MutableSet<*> -> getStringSet(key, default as MutableSet<String>) as T
            else -> {
                throw IllegalArgumentException("不支持的类型")
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    /**
     * 添加数据
     */
    fun <T> put(key: String, value: T){
        val edit = sp.edit()
        when(value){
            is Int -> edit.putInt(key,value as Int)
            is Float -> edit.putFloat(key, value as Float)
            is Long -> edit.putLong(key, value as Long)
            is Boolean -> edit.putBoolean(key, value as Boolean)
            is String -> edit.putString(key, value as String)
            is MutableSet<*> -> edit.putStringSet(key, value as MutableSet<String>)
            else -> {
                edit.putString(key,value as String)
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
