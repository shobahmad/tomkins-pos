package com.erebor.tomkins.pos.view.custom

import android.view.View
import android.view.ViewGroup

import androidx.databinding.BindingAdapter

object CommonBindingUtils {

    @BindingAdapter("android:layout_width")
    fun setlayoutWidth(view: View, size: Float) {
        val layoutParams = view.layoutParams
        layoutParams.width = size.toInt()
        view.layoutParams = layoutParams
    }

    @BindingAdapter("android:layout_height")
    fun setlayoutHeight(view: View, size: Float) {
        val layoutParams = view.layoutParams
        layoutParams.height = size.toInt()
        view.layoutParams = layoutParams
    }

    @BindingAdapter("android:layout_width")
    fun setLayoutWidth(view: View, width: Int) {
        val layoutParams = view.layoutParams
        layoutParams.width = width
        view.layoutParams = layoutParams
    }

    @BindingAdapter("android:layout_height")
    fun setlayoutHeight(view: View, size: Int) {
        val layoutParams = view.layoutParams
        layoutParams.height = size
        view.layoutParams = layoutParams
    }

}
