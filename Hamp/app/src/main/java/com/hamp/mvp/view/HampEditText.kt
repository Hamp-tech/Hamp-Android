package com.hamp.mvp.view

import android.content.Context
import android.graphics.PorterDuff
import android.os.Build
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatEditText
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import com.hamp.R
import com.hamp.extension.px
import org.jetbrains.anko.applyRecursively


class HampEditText : AppCompatEditText, View.OnFocusChangeListener, TextWatcher {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            setTextAppearance(R.style.HelveticaNeueMedium20_Razzmatazz)
        } else {
            setTextAppearance(context, R.style.HelveticaNeueMedium20_Razzmatazz)
        }

        setEms(10)
        setPadding(0, 4.px, 0, 15.px)
        gravity = Gravity.CENTER
        setColorFilter(ContextCompat.getColor(context, R.color.black))
        onFocusChangeListener = this
        addTextChangedListener(this)
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (v is HampEditText) {
            if (hasFocus) v.hint = ""
            else v.hint = v.tag as String
        }
    }

    override fun afterTextChanged(s: Editable?) {
        if (s.isNullOrBlank()) {
            setColorFilter(ContextCompat.getColor(context, R.color.black))
        } else {
            setColorFilter(ContextCompat.getColor(context, R.color.cerise_pink))
        }
    }

    override fun onTextChanged(text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    fun setColorFilter(color: Int) {
        background.mutate().setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }

    override fun setError(error: CharSequence?) {
        super.setError(error)
        if (error != null) startAnimation(shakeAnimation())
    }

    private fun shakeAnimation(): Animation {
        return TranslateAnimation(0f, 10f, 0f, 0f).apply {
            duration = 500
            interpolator = CycleInterpolator(7f)
        }
    }
}