package com.erebor.tomkins.pos.view.custom;

import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.BindingAdapter;

public class CommonBindingUtils {

    @BindingAdapter("android:layout_width")
    public static void setlayoutWidth(View view, float size) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = (int) size;
        view.setLayoutParams(layoutParams);
    }
    @BindingAdapter("android:layout_height")
    public static void setlayoutHeight(View view, float size) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) size;
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("android:layout_width")
    public static void setLayoutWidth(View view, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        view.setLayoutParams(layoutParams);
    }

    @BindingAdapter("android:layout_height")
    public static void setlayoutHeight(View view, int size) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = size;
        view.setLayoutParams(layoutParams);
    }

}
