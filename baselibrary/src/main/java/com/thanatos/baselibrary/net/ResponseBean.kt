package com.thanatos.baselibrary.net

import com.google.gson.annotations.SerializedName

//响应体信息
data class ResponseBean(@SerializedName("code") val errorCode: Int,
                               @SerializedName("msg") val errorMsg: String,
                               @SerializedName("data") val data: Any ){
    /**
     * 是否获取数据成功
     */
    public fun isSuccessful() = errorCode == 0
}
interface IBean
data class IndexBannerBean(val desc: String,
                           val id: Int,
                           val imagePath: String,
                           val isVisible: Int,
                           val order: Int,
                           val title: String,
                           val type: Int,
                           val url: String) : IBean

//响应异常信息
data class ResponseException(val code: Int?, val msg: String?)

