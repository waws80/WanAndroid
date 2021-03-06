package com.thanatos.baselibrary.data

import com.thanatos.baselibrary.ext.printLog
import com.thanatos.baselibrary.ext.saveSp
import com.thanatos.baselibrary.gson.GsonUtil
import com.thanatos.baselibrary.net.*
import com.thanatos.baselibrary.sputil.SpUtil
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class RemoteData private constructor(){

    companion object {
        /**
         * 获取当前类唯一实类对象
         */
        fun getInstance() = RemoteData()
    }

    public val articleData = ArticleData()

    abstract class AbsData{

        protected open fun apiObservable(observable: Observable<Any>): Observable<Any> =
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    }

    class ArticleData: AbsData(){

        private val mServicer = HttpManager.getRetrofit().create(ArticleService::class.java)

        //获取首页轮播图
        fun getIndexBanner(next: (Boolean, List<IndexBannerBean>, String) -> Unit){

             apiObservable(mServicer.getIndexBanner())
                     .subscribe(HttpCallBack.getInstance().callBack({
                         data, res ->
                         var beans: MutableList<IndexBannerBean>? = GsonUtil.fromList(data,IndexBannerBean::class.java)
                         if (beans == null){
                             beans = mutableListOf()
                         }
                         next.invoke(res.isSuccessful(),beans, res.msg)
                     }))
        }

        //进行登录操作
        fun login(account: String, password: String, next:(Boolean, String) -> Unit){
            val table = HashMap<String, String>()
            table["username"] = account
            table["password"] = password
            apiObservable(mServicer.login(table))
                    .subscribe(HttpCallBack.getInstance().callBack { any, responseException ->
                        if (responseException.isSuccessful()){
                            //保存用户信息
                            val userBean = GsonUtil.fromObject(any,UserBean::class.java)
                            GsonUtil.toJson(userBean).saveSp("userBean")
                            userBean.id.saveSp("userId")
                            SpUtil.put("isLogin",true)

                        }
                        next.invoke(responseException.isSuccessful(),responseException.msg)

                    })
        }

        //进行注册操作
        fun register(account: String, password: String, next: (Boolean, String) -> Unit) {

            val table = HashMap<String, String>()
            table["username"] = account
            table["password"] = password
            table["repassword"] = password
            apiObservable(mServicer.register(table))
                    .subscribe(HttpCallBack.getInstance().callBack { any, responseException ->
                        if (responseException.isSuccessful()){
                            //保存用户信息
                            val userBean = GsonUtil.fromObject(any,UserBean::class.java)
                            GsonUtil.toJson(userBean).saveSp("userBean")
                            userBean.id.saveSp("userId")
                            SpUtil.put("isLogin",true)
                        }
                        next.invoke(responseException.isSuccessful(),responseException.msg)
                    })
        }

        //获取首页文章列表
        fun getArticleList(page: Int, next: (Boolean, ArticleListBean, String) -> Unit) {
            apiObservable(mServicer.getArticleList(page))
                    .subscribe(HttpCallBack.getInstance().callBack { any, responseException ->
                        next.invoke(responseException.isSuccessful(),
                                GsonUtil.fromObject(any,ArticleListBean::class.java),
                                responseException.msg)
                    })
        }

        fun collectArticle(id: Int, next: (Boolean, String) -> Unit) {
            apiObservable(mServicer.collectArticle(id))
                    .subscribe(HttpCallBack.getInstance().callBack { any, responseException ->
                        next(responseException.isSuccessful(),responseException.msg)
                    })
        }


        fun unCollectArticle(id: Int, next: (Boolean, String) -> Unit) {
            apiObservable(mServicer.unCollectArticle(id))
                    .subscribe(HttpCallBack.getInstance().callBack { any, responseException ->
                        next(responseException.isSuccessful(), responseException.msg)
                    })
        }

    }
}
