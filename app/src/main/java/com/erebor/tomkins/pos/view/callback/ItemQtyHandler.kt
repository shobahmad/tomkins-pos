package com.erebor.tomkins.pos.view.callback

import android.view.View

interface ItemQtyHandler {
    fun onPositiveButtonClick(item: View)
    fun onNegativeButtonClick(item: View)
}

