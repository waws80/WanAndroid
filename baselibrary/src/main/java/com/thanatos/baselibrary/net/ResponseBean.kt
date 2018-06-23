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

//首页轮播图banner实体类
data class IndexBannerBean(val desc: String,
                           val id: Int,
                           val imagePath: String,
                           val isVisible: Int,
                           val order: Int,
                           val title: String,
                           val type: Int,
                           val url: String)

//用户信息实体类
data class UserBean(val username: String, val password: String)



