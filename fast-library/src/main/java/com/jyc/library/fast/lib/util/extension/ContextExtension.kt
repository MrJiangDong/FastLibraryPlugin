package com.jyc.library.fast.lib.util.extension

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.jyc.library.fast.lib.util.view.FastViewUtil


/// @author jyc
/// 创建日期：2021/8/10
/// 描述：ContextExtension
fun Context.getMyColor(@ColorRes colorRes: Int): Int {
    return ContextCompat.getColor(this, colorRes)
}

fun Context.fastStartActivity(cls: Class<*>, bundle: Bundle?) {
    if (FastViewUtil.isActivityDestroyed(this)) return
    val intent = Intent(this, cls)
    if (bundle != null) {
        intent.putExtras(bundle)
    }
    startActivity(intent)
}

fun Context.fastStartActivity(cls: Class<*>) {
    val intent = Intent(this, cls)
    startActivity(intent)
}