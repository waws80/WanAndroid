package com.thanatos.baselibrary

import java.util.regex.Pattern

/**
 *  功能描述: 正则验证工具类
 *  @className:
 *  @author: thanatos
 *  @createTime: 2018/6/22
 *  @updateTime: 2018/6/22 16:39
 */
object RegexUtil{

    /**
     * 正则表达式:验证用户名(不包含中文和特殊字符)如果用户名使用手机号码或邮箱 则结合手机号验证和邮箱验证
     */
    val REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$"

    /**
     * 正则表达式:验证密码(不包含特殊字符)
     */
    val REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$"

    val REGEX_MOBILE_SIMPLE = "^[1]\\d{10}$"

    /**
     * 正则表达式:验证手机号
     */
    val REGEX_MOBILE = "^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$"

    /**
     * 正则表达式:验证邮箱
     */
    val REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"

    /**
     * 正则表达式:验证汉字(1-9个汉字)  {1,9} 自定义区间
     */
    val REGEX_CHINESE = "^[\u4e00-\u9fa5]{1,9}$"

    /**
     * 正则表达式:验证身份证
     */
    val REGEX_ID_CARD = "(^\\d{15}$)|(^\\d{17}([0-9]|X)$)"

    /**
     * 正则表达式:验证URL
     */
    val REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?"

    /**
     * 正则表达式:验证IP地址
     */
    val REGEX_IP_ADDR = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})"

    /**
     * 校验用户名
     * @param username
     * @return 校验通过返回true，否则返回false
     */
    fun isUserName(username: String): Boolean {
        return Pattern.matches(REGEX_USERNAME, username)
    }

    /**
     * 校验密码
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    fun isPassword(password: String): Boolean {
        return Pattern.matches(REGEX_PASSWORD, password)
    }

    /**
     * 校验手机号
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    fun isMobile(mobile: String): Boolean {
        return Pattern.matches(REGEX_MOBILE, mobile)
    }

    /**
     * 校验手机号(简单验证)
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    fun isMobileSimple(mobile: String): Boolean {
        return Pattern.matches(REGEX_MOBILE_SIMPLE, mobile)
    }

    /**
     * 校验邮箱
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    fun isEmail(email: String): Boolean {
        return Pattern.matches(REGEX_EMAIL, email)
    }

    /**
     * 校验汉字
     * @param chinese
     * @return 校验通过返回true，否则返回false
     */
    fun isChinese(chinese: String): Boolean {
        return Pattern.matches(REGEX_CHINESE, chinese)
    }

    /**
     * 校验身份证
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    fun isIDCard(idCard: String): Boolean {
        return Pattern.matches(REGEX_ID_CARD, idCard)
    }

    /**
     * 校验URL
     * @param url
     * @return 校验通过返回true，否则返回false
     */
    fun isUrl(url: String): Boolean {
        return Pattern.matches(REGEX_URL, url)
    }

    /**
     * 校验IP地址
     * @param ipAddress
     * @return
     */
    fun isIPAddress(ipAddress: String): Boolean {
        return Pattern.matches(REGEX_IP_ADDR, ipAddress)
    }

}