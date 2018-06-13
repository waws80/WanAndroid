package com.thanatos.wanandroid

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.widget.RelativeLayout
import com.thanatos.baselibrary.base.BaseActivity
import com.thanatos.baselibrary.ext.setStatusBar
import pw.androidthanatos.annotation.Path
import top.waws.premission.PermissionUtil
import java.util.*

@Path("app_main")
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PermissionUtil.getInstance().build(this)
                .mustAgree()
                .request(Arrays.asList(Manifest.permission.CAMERA))
                .execute { code ->
                    log(String().plus(code))
                }
    }


    override fun onResume() {
        super.onResume()

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
