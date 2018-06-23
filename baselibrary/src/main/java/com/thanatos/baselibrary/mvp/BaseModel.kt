package com.thanatos.baselibrary.mvp

import com.thanatos.baselibrary.data.LocalData
import com.thanatos.baselibrary.data.RemoteData

/**
 *  功能描述: mode基类
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/22
 *  @updateTime: 2018/6/22 15:58
 */
abstract class BaseModel{

    /**
     * 本地仓库获取数据
     */
    protected val mLocalData: LocalData = LocalData.getInstance()


    /**
     * 远程获取数据
     */
    protected val mRemoteData: RemoteData = RemoteData.getInstance()
}
