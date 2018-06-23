package com.thanatos.baselibrary.data

class LocalData private constructor(){

    companion object {
        /**
         * 获取当前类唯一实类对象
         */
        fun getInstance() = LocalData()
    }

}