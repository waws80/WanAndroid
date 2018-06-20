package com.thanatos.baselibrary.gson

import android.support.annotation.NonNull
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonParser

object GsonUtil {

    private val gson = GsonBuilder().serializeNulls().create()

    fun getGson(): Gson{
        return gson
    }

    fun toJson(value: Any) = gson.toJson(value)

    fun fromObject(@NonNull json: Any, clazz: Class<Any>) = gson.fromJson(gson.toJson(json),clazz)

    fun <D> fromList(@NonNull json: Any, clazz: Class<D>): MutableList<D>{
        try {
            val array = JsonParser().parse(gson.toJson(json)).asJsonArray
            val list = mutableListOf<D>()
            array.forEach {
                val item = it.asJsonObject
                val d = gson.fromJson(item,clazz)
                list.add(d)
            }
            return list
        }catch (e: Exception){
            e.printStackTrace()
        }
        return mutableListOf()
    }
}