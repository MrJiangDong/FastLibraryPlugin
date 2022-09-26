package com.jyc.library.fast.lib.util

import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import java.lang.ref.WeakReference


/// @author jyc
/// 创建日期：2021/7/20
/// 描述：ActivityManager
/// 提供前后台状态监听 以及栈顶activity的服务
class ActivityManager private constructor() {

    private val activityRefs = ArrayList<WeakReference<Activity>>()
    private val frontBackCallbacks = ArrayList<FrontBackCallback>()
    private var activityStartCount = 0
    var front = true

    fun init(application: Application) {
        application.registerActivityLifecycleCallbacks(InnerActivityLifecycleCallbacks())
    }

    inner class InnerActivityLifecycleCallbacks :
        Application.ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activityRefs.add(WeakReference(activity))
//            Toast.makeText(activity.applicationContext, "启动", Toast.LENGTH_LONG).show()
        }

        override fun onActivityStarted(activity: Activity) {
            activityStartCount++
            //activityStartCount>0 说明应用处在可见状态,也就是前台
            //！front 之前是不是在后台
            if (!front && activityStartCount > 0) {
                front = true
                onFrontBackChanged(front)
            }
        }

        override fun onActivityResumed(activity: Activity) {

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {
            activityStartCount--
            if (activityStartCount <= 0 && front) {
                front = false
                onFrontBackChanged(front)
            }
        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            for (activityRef in activityRefs) {
                if (activityRef != null && activityRef.get() == activity) {
                    activityRefs.remove(activityRef);
                    break
                }
            }
        }

    }

    private fun onFrontBackChanged(front: Boolean) {
        for (callback in frontBackCallbacks) {
            callback.onChanged(front)
        }
    }

    val topActivity: Activity?
        get() {
            return if (activityRefs.size <= 0) {
                null
            } else {
                activityRefs[activityRefs.size - 1].get()
            }
        }

    /**
     * 找出栈顶不为空，且没有被销毁的activity
     */
    fun getTopActivity(onlyAlive: Boolean): Activity? {
        if (activityRefs.size <= 0) {
            return null
        } else {
            val activityRef = activityRefs[activityRefs.size - 1]
            val activity = activityRef.get();
            if (onlyAlive) {
                if (activityRef == null || activity!!.isFinishing
                    || (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && activity.isDestroyed)
                ) {
                    activityRefs.remove(activityRef)
                    return getTopActivity(onlyAlive)
                }
            }
            return activity
        }
    }

    fun getActivityRefs(): ArrayList<WeakReference<Activity>> {
        return activityRefs
    }

    fun addFrontBackCallback(callback: FrontBackCallback) {
        if (!frontBackCallbacks.contains(callback)) {
            frontBackCallbacks.add(callback)
        }
    }

    fun removeFrontBackCallback(callback: FrontBackCallback) {
        frontBackCallbacks.remove(callback)
    }

    interface FrontBackCallback {
        fun onChanged(front: Boolean)
    }

    companion object {
        @JvmStatic
        val instance: ActivityManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            ActivityManager()
        }
    }
}