package com.thanatos.article.hotnet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.thanatos.article.R
import pw.androidthanatos.annotation.Path

/**
 *  功能描述: 常用网站
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/29
 *  @updateTime: 2018/6/29 16:45
 */
@Path("/article/hotNet")
class HotNetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.article_activity_hot_net)
    }
}
