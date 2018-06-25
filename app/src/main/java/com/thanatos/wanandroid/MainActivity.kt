package com.thanatos.wanandroid

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.NonNull
import android.support.design.widget.TabLayout
import android.support.v4.app.FragmentPagerAdapter
import android.transition.Fade
import android.view.Gravity
import android.widget.CheckBox
import com.thanatos.article.chapter.ChapterIndexFragment
import com.thanatos.article.index.ArticleIndexFragment
import com.thanatos.baselibrary.mvp.BaseMvpActivity
import com.thanatos.baselibrary.mvp.BaseView
import com.thanatos.mine.index.MainIndexFragment
import com.thanatos.video.index.VideoIndexFragment
import kotlinx.android.synthetic.main.activity_main.*
import pw.androidthanatos.annotation.Path

/**
 *  功能描述: 首页
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/25
 *  @updateTime: 2018/6/25 17:48
 */
@Path("/app/main")
class MainActivity : BaseMvpActivity<BaseView,TestPresenter>() {

    override fun getPresenter(): TestPresenter {
        return TestPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fade = Fade()
        fade.duration = 500
        window.enterTransition = fade
        window.exitTransition = fade
        setContentView(R.layout.activity_main)
        //设置导航栏
        setNavigationColor()
        initUI()
    }


    private fun initUI(){
        //添加fragment
        val fragments = arrayOf(ArticleIndexFragment(),
                ChapterIndexFragment(), VideoIndexFragment(), MainIndexFragment())
        //添加tab
        tabLayout.addTab(getTabView("首页",R.drawable.selector_home))
        tabLayout.addTab(getTabView("体系",R.drawable.selector_type))
        tabLayout.addTab(getTabView("视频",R.drawable.selector_video))
        tabLayout.addTab(getTabView("我的",R.drawable.selector_mine))
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                (tab.customView as CheckBox)
                        .isChecked  = false
            }

            override fun onTabSelected(tab: TabLayout.Tab) {
                (tab.customView as CheckBox)
                        .isChecked  = true
                mainPager.currentItem = tab.position
            }

        })

        (tabLayout.getTabAt(0)?.customView as CheckBox).isChecked = true
        //所有fragment都缓存
        mainPager.offscreenPageLimit = fragments.size

        mainPager.adapter = object : FragmentPagerAdapter(supportFragmentManager){
            override fun getItem(position: Int) = fragments[position]
            override fun getCount() = 4
        }
        mainPager.setOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))
    }

    private fun getTabView(@NonNull text: String, @DrawableRes res: Int): TabLayout.Tab{
        val cb = CheckBox(this)
        cb.buttonDrawable = null
        val drawable = resources.getDrawable(res)
        drawable.setBounds(0,0,drawable.intrinsicWidth,drawable.intrinsicHeight)
        cb.setCompoundDrawables(null,drawable,null,null)
        cb.text = text
        cb.gravity = Gravity.CENTER
        cb.textSize = 12f
        cb.isChecked = false
        cb.isClickable = false
        cb.setTextColor(getColorStateList())
        cb.setBackgroundColor(Color.parseColor("#EBEBEB"))
        return  tabLayout.newTab().setCustomView(cb)
    }

    private fun getColorStateList(): ColorStateList{
        val checkedarr = IntArray(1)
        checkedarr[0] = android.R.attr.state_checked
        val uncheckedarr = IntArray(1)
        uncheckedarr[0] = -android.R.attr.state_checked
        val states:Array<IntArray> = arrayOf(checkedarr,uncheckedarr)
        val colors: IntArray = IntArray(2)
        colors[0] = resources.getColor(R.color.colorPrimaryDark)
        colors[1] = Color.parseColor("#999999")
        return ColorStateList(states,colors)
    }


}
