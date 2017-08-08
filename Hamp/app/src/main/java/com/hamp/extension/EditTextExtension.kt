package com.hamp.extension

import android.graphics.PorterDuff
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.hamp.R

fun EditText.changeBackgroundTextWatcher() {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s.isNullOrBlank()) {
                this@changeBackgroundTextWatcher.background.mutate()
                        .setColorFilter(ContextCompat.getColor(context, R.color.black),
                                PorterDuff.Mode.SRC_ATOP)
            } else {
                this@changeBackgroundTextWatcher.background.mutate()
                        .setColorFilter(ContextCompat.getColor(context, R.color.razzmatazz),
                                PorterDuff.Mode.SRC_ATOP)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}
