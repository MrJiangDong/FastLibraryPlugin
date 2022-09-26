package com.jyc.fast.library

import android.app.Application
import com.jyc.library.fast.lib.util.ActivityManager
import com.jyc.library.fast.lib.util.crash.CrashHandler


/// @author jyc
/// 创建日期：2021/4/25
/// 描述：MyApplication
class MyApplication : Application() {
    private var isDebug = true
    override fun onCreate() {
        super.onCreate()

        //指定一个全局Crash处理器
        CrashHandler.init()


        ActivityManager.instance.init(this)
    }

}