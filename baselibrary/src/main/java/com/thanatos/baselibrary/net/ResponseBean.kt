package com.thanatos.baselibrary.net

//响应体信息
data class ResponseBean(val errorCode: Int,
                        val errorMsg: String,
                        val data: Any? ){
    /**
     * 是否获取数据成功
     */
    public fun isSuccessful() = errorCode == 0
}

//响应异常信息
data class ResponseException(val code: Int, val msg: String){
    /**
     * 是否获取数据成功
     */
    public fun isSuccessful() = code == 0
}

//用户信息实体类
data class UserBean(val username: String, val password: String, val id: String)

//首页轮播图banner实体类
data class IndexBannerBean(val desc: String,
                           val id: Int,
                           val imagePath: String,
                           val isVisible: Int,
                           val order: Int,
                           val title: String,
                           val type: Int,
                           val url: String)

//首页文章列表
data class ArticleListBean(val curPage: Int,
                           val pageCount: Int,
                           val total: Int,
                           val datas: MutableList<ArticleBean>)

//首页文章实体类
data class ArticleBean(val author: String, //作者
                       val chapterName: String, //二级分类
                       val title: String, //标题
                       val niceDate: String, //时间
                       val link: String, //连接
                       val id: Int, //文章id
                       var collect: Boolean,//是否收藏
                       val superChapterName: String //一级分类
                        )



