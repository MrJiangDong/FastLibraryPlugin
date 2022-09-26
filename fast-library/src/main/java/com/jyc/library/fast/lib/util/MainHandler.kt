package com.jyc.library.fast.lib.util

import android.os.Handler
import android.os.Looper
import android.os.Message


/// @author jyc
/// 创建日期：2021/8/27
/// 描述：MainHandler
object MainHandler {
    private val handler = Handler(Looper.getMainLooper())

    fun post(runnable: Runnable) {
        handler.post(runnable)
    }

    fun postDelay(delayMills: Long, runnable: Runnable) {
        handler.postDelayed(runnable, delayMills)
    }

    /*
        任务插到最前面执行
     */
    fun sendAtFrontOfQueue(runnable: Runnable) {
        val msg = Message.obtain(handler, runnable)
        handler.sendMessageAtFrontOfQueue(msg)
    }

    fun remove(runnable: Runnable) {
        handler.removeCallbacks(runnable)
    }
}