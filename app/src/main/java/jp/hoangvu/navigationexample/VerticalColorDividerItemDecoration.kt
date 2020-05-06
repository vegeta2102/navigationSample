package jp.hoangvu.navigationexample

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class VerticalColorDividerItemDecoration(
    @ColorInt color: Int,
    private val leftPaddingPixels: Int = 0,
    private val rightPaddingPixels: Int = 0,
    private val heightPixels: Int = 0,
    private val isLastDividerVisible: Boolean = true
) : RecyclerView.ItemDecoration() {

    private val divider: ColorDrawable = ColorDrawable(color)

    private val bounds = Rect()

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        parent.layoutManager ?: return
        drawVertical(c, parent)
    }

    private fun drawVertical(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int

        if (parent.clipToPadding) {
            left = parent.paddingLeft + leftPaddingPixels
            right = parent.width - parent.paddingRight - rightPaddingPixels
            canvas.clipRect(
                left, parent.paddingTop, right,
                parent.height - parent.paddingBottom
            )
        } else {
            left = leftPaddingPixels
            right = parent.width - rightPaddingPixels
        }

        val childCount = parent.childCount - if (isLastDividerVisible) { 0 } else { 1 }
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val bottom = bounds.bottom + child.translationY.roundToInt()
            val top = bottom - heightPixels
            divider.setBounds(left, top, right, bottom)
            divider.draw(canvas)
        }
        canvas.restore()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(0, 0, 0, heightPixels)
    }
}
