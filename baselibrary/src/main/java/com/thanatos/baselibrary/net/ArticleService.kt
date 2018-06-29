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

    //首页文章列表
    @GET("article/list/{page}/json")
    fun getArticleList(@Path("page") page: Int): Observable<Any>

    //收藏站内文章
    @POST("lg/collect/{id}/json")
    fun collectArticle(@Path("id") id: Int): Observable<Any>

    //取消文章的收藏,文章列表
    @POST("lg/uncollect_originId/{id}/json")
    fun unCollectArticle(@Path("id") id: Int): Observable<Any>

}