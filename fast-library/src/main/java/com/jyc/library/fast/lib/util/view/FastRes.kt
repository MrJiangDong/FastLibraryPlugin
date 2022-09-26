package com.jyc.library.fast.lib.util.view

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.jyc.library.fast.lib.util.AppGlobals


/// @author jyc
/// 创建日期：2021/9/10
/// 描述：FastRes
object FastRes {
    fun getString(@StringRes id: Int): String {
        return context().getString(id)
    }

    fun getString(@StringRes id: Int, vararg formatArgs: Any?): String {
        return context().getString(id, *formatArgs)
    }

    fun getColor(@ColorRes id: Int): Int {
        return ContextCompat.getColor(context(), id)
    }

    fun getDrawable(@DrawableRes id: Int): Drawable? {
        return ContextCompat.getDrawable(context(), id)
    }

    private fun context(): Context {
        return AppGlobals.get() as Context
    }
}