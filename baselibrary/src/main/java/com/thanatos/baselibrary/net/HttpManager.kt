package com.thanatos.baselibrary.net

import com.google.gson.Gson
import com.thanatos.baselibrary.BuildConfig
import com.thanatos.baselibrary.gson.GsonUtil
import com.thanatos.baselibrary.sputil.SpUtil
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HttpManager {

    /**
     * 实类化okhttp builder
     */
    private val okHttpClientBuilder = OkHttpClient.Builder()

    private val retrofitBuilder = Retrofit.Builder()

    init {
        //添加 okhttp body 日志
        @Suppress("ConstantConditionIf")
        if (BuildConfig.APP_DEBUG){
            okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        //设置okhttp
        okHttpClientBuilder.connectTimeout(60,TimeUnit.SECONDS)
                .readTimeout(60,TimeUnit.SECONDS)
                .writeTimeout(60,TimeUnit.SECONDS)
                .cookieJar(object : CookieJar{
                    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
                        SpUtil.put(url.host(),GsonUtil.toJson(cookies))
                    }

                    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
                        val json: String = SpUtil.get(url.host(), String())
                        return GsonUtil.fromJsonList(json,Cookie::class.java)
                    }

                })

        //设置retrofit
        retrofitBuilder.baseUrl(BuildConfig.APP_BASE_URL_ARTICLE)
                //json 转换器
                .addConverterFactory(GsonConverterFactory.create(GsonUtil.getGson()))
                // rxjava 转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //okhttp客户端
                .client(okHttpClientBuilder.build())
    }

    /**
     * 获取retrofit
     */
    fun getRetrofit() = retrofitBuilder.build()

    /**
     * 获取okhttp
     */
    fun getOkHttp() = okHttpClientBuilder.build()
}