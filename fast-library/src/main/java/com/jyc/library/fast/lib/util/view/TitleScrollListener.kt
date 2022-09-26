package com.jyc.library.fast.lib.util.view

import android.graphics.Color
import androidx.recyclerview.widget.RecyclerView
import com.jyc.library.fast.lib.util.view.FastColorUtil
import kotlin.math.abs
import kotlin.math.min


/// @author jyc
/// 创建日期：2021/9/8
/// 描述：TitleScrollListener
class TitleScrollListener(thresholdDp: Float = 100f, val callback: (Int) -> Unit) :
    RecyclerView.OnScrollListener() {
    private val thresholdPx = FastDisplayUtil.dp2px(thresholdDp)
    private var lastFraction = 0f

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        //在这里面 我们需要去判断列表的滑动距离 然后跟thresholdPx 做运算，计算当前滑动状态
        //计算出一个新的颜色值 transpanrent ----- white
        //recyclerView.scrollY
        //dy

        val viewHolder = recyclerView.findViewHolderForAdapterPosition(0) ?: return
        val top = abs(viewHolder.itemView.top).toFloat()

        //计算出当前滑动百分比
        val fraction = top / thresholdPx

        if (lastFraction > 1f) {
            lastFraction = fraction
            return
        }

        val newColor =
            FastColorUtil.getCurrentColor(Color.TRANSPARENT, Color.WHITE, min(fraction, 1f))
        callback(newColor)

        lastFraction = fraction
    }
}