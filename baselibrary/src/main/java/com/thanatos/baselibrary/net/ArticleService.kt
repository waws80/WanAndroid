package com.thanatos.baselibrary.net

import io.reactivex.Observable
import retrofit2.http.*

//文章service
interface ArticleService {


    //用户信息

    //用户登录
    @POST("user/login")
    @FormUrlEncoded
    fun login(@FieldMap table: HashMap<String, String>): Observable<Any>

    //用户注册
    @POST("user/register")
    @FormUrlEncoded
    fun register(@FieldMap table: HashMap<String, String>): Observable<Any>

    //首页---------------------------------

    //首页轮播图
    @GET("banner/json")
    fun getIndexBanner(): Observable<Any>

}