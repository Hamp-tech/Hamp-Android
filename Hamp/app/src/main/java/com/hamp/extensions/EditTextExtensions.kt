package com.hamp.extensions

import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import android.widget.EditText

fun EditText.shake() {
    this.startAnimation(TranslateAnimation(0f, 10f, 0f, 0f).apply {
        duration = 500
        interpolator = CycleInterpolator(7f)
    })
}

fun EditText.setEditMode(editMode: Boolean) {
    isClickable = editMode
    isCursorVisible = editMode
    isFocusable = editMode
    isFocusableInTouchMode = editMode
}

fun EditText.trim() = this.text.toString().trim()