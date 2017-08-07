package com.hamp.mvp.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.hamp.R
import kotlinx.android.synthetic.main.custom_hamp_edittext.view.*

class HampEditText @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    val xmlns = "http://schemas.android.com/apk/res/android"
    var hintResource = 0

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_hamp_edittext, this, true)
        layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val inputType = attrs?.getAttributeResourceValue(xmlns, "inputType", 0)
        hintResource = attrs?.getAttributeResourceValue(xmlns, "hint", 0) ?: 0

//        if (attrs != null) {
//            val a = context.obtainStyledAttributes(attrs, R.styleable.CustomUnityButton)
//            if (a.hasValue(R.styleable.CustomUnityButton_title)) {
//                title.text = a.getString(R.styleable.CustomUnityButton_title)
//            }
//        }

        inputType?.let { hampEditText.inputType = it }

        hampEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) hampEditText.hint = ""
            else hampEditText.hint = context.getString(hintResource)
        }
    }
}