package com.jyc.library.fast.lib.util

import android.content.Context
import android.content.SharedPreferences
import java.util.*


/// @author jyc
/// 创建日期：2021/8/11
/// 描述：SPUtil
object SPUtil {

    private const val CACHE_FILE = "cache_file"
    private const val Splitter = "%Splitter%"

    fun putString(key: String, value: String) {
        val shared = getShared()
        shared?.edit()?.putString(key, value)?.apply()
    }

    fun getString(key: String): String? {
        val shared = getShared()
        if (shared != null) {
            return shared.getString(key, null)
        }
        return null
    }

    fun putBoolean(key: String, value: Boolean) {
        val shared = getShared()
        shared?.edit()?.putBoolean(key, value)?.apply()
    }


    fun getBoolean(key: String): Boolean {
        val shared = getShared()
        if (shared != null) {
            return shared.getBoolean(key, false)
        }
        return false
    }

    fun putInt(key: String, value: Int) {
        val shared = getShared()
        shared?.edit()?.putInt(key, value)?.apply()
    }


    fun getInt(key: String): Int {
        val shared = getShared()
        if (shared != null) {
            return shared.getInt(key, 0)
        }
        return 0
    }

    fun putValue(key: String, stringArrayList: List<String>) {
        var value = ""
        for (i in stringArrayList.indices) {
            value = value + Splitter + stringArrayList[i]
        }
        value = value.replaceFirst(Splitter.toRegex(), "")
        putString(key, value)
    }

    fun getValue(key: String): ArrayList<String> {
        val value = getString(key)

        if (value.isNullOrEmpty()) return ArrayList()

        val valueArray: Array<String> =
            value.split(Splitter).toTypedArray()

        val stringArrayList = ArrayList<String>()

        stringArrayList.addAll(Arrays.asList(*valueArray))

        return stringArrayList
    }

    private fun getShared(): SharedPreferences? {
        val application = AppGlobals.get()
        if (application != null) {
            return application.getSharedPreferences(CACHE_FILE, Context.MODE_PRIVATE)
        }
        return null
    }
}