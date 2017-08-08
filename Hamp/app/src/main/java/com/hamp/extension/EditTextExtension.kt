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
                this@changeBackgroundTextWatcher.setColorFilter(ContextCompat.
                        getColor(context, R.color.black))
            } else {
                this@changeBackgroundTextWatcher.setColorFilter(ContextCompat.
                        getColor(context, R.color.cerise_pink))
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}

fun EditText.setColorFilter(color: Int) {
    background.mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
}
