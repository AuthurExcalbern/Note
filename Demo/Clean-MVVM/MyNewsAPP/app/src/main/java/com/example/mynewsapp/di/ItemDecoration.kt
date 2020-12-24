package com.example.mynewsapp.di

import android.R
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import androidx.recyclerview.widget.StaggeredGridLayoutManager


class ItemDecoration(context: Context) : ItemDecoration() {
    private val mDivider: Drawable?
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        drawHorizontal(c, parent)
        drawVertical(c, parent)
    }

    private fun getSpanCount(parent: RecyclerView): Int {
        // 列数
        var spanCount = -1
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            spanCount = layoutManager.spanCount
        } else if (layoutManager is StaggeredGridLayoutManager) {
            spanCount = layoutManager
                .spanCount
        }
        return spanCount
    }

    private fun drawHorizontal(c: Canvas?, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val left: Int = child.left - params.leftMargin
            val right: Int = (child.right + params.rightMargin
                    + mDivider!!.intrinsicWidth)
            val top: Int = child.bottom + params.bottomMargin
            val bottom = top + mDivider.intrinsicHeight
            mDivider.setBounds(left, top, right, bottom)
            if (c != null) {
                mDivider.draw(c)
            }
        }
    }

    private fun drawVertical(c: Canvas?, parent: RecyclerView) {
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val params = child
                .layoutParams as RecyclerView.LayoutParams
            val top: Int = child.top - params.topMargin
            val bottom: Int = child.bottom + params.bottomMargin
            val left: Int = child.right + params.rightMargin
            val right = left + mDivider!!.intrinsicWidth
            mDivider.setBounds(left, top, right, bottom)
            if (c != null) {
                mDivider.draw(c)
            }
        }
    }

    private fun isLastColumn(
        parent: RecyclerView, pos: Int, spanCount: Int,
        childCount: Int
    ): Boolean {
        var childCount = childCount
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            if ((pos + 1) % spanCount == 0) // 如果是最后一列，则不需要绘制右边
            {
                return true
            }
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager
                .orientation
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((pos + 1) % spanCount == 0) // 如果是最后一列，则不需要绘制右边
                {
                    return true
                }
            } else {
                childCount -= childCount % spanCount
                if (pos >= childCount) // 如果是最后一列，则不需要绘制右边
                    return true
            }
        }
        return false
    }

    private fun isLastRaw(
        parent: RecyclerView, pos: Int, spanCount: Int,
        childCount: Int
    ): Boolean {
        var childCount = childCount
        val layoutManager = parent.layoutManager
        if (layoutManager is GridLayoutManager) {
            childCount -= childCount % spanCount
            if (pos >= childCount) // 如果是最后一行，则不需要绘制底部
                return true
        } else if (layoutManager is StaggeredGridLayoutManager) {
            val orientation = layoutManager
                .orientation
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount -= childCount % spanCount
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount) return true
            } else  // StaggeredGridLayoutManager 且横向滚动
            {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true
                }
            }
        }
        return false
    }

    override fun getItemOffsets(
        outRect: Rect, itemPosition: Int,
        parent: RecyclerView
    ) {
        val spanCount = getSpanCount(parent)
        val childCount = parent.adapter!!.itemCount
        when {
            isLastRaw(parent, itemPosition, spanCount, childCount) // 如果是最后一行，则不需要绘制底部
            -> {
                outRect.set(0, 0, mDivider!!.intrinsicWidth, 0)
            }
            isLastColumn(parent, itemPosition, spanCount, childCount) // 如果是最后一列，则不需要绘制右边
            -> {
                outRect.set(0, 0, 0, mDivider!!.intrinsicHeight)
            }
            else -> {
                outRect.set(
                    0, 0, mDivider!!.intrinsicWidth,
                    mDivider.intrinsicHeight
                )
            }
        }
    }

    companion object {
        private val ATTRS = intArrayOf(R.attr.listDivider)
    }

    init {
        val a: TypedArray = context.obtainStyledAttributes(ATTRS)
        mDivider = a.getDrawable(0)
        a.recycle()
    }
}