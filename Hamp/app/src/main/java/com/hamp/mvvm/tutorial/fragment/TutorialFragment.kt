package com.hamp.mvvm.tutorial.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hamp.R
import com.hamp.common.BaseFragment
import com.hamp.extensions.loadImg
import com.hamp.mvvm.tutorial.TutorialActivity
import kotlinx.android.synthetic.main.fragment_tutorial.*

class TutorialFragment : BaseFragment() {

    private var image = 0
    lateinit private var title: String
    lateinit private var info: String

    companion object {
        fun create(image: Int, title: String, info: String) = TutorialFragment().apply {
            this.image = image
            this.title = title
            this.info = info
        }
    }

    private val tutorialActivity: TutorialActivity
        get() = activity as TutorialActivity

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.fragment_tutorial, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageTutorial.loadImg(image)
        titleTutorial.text = title
        infoTutorial.text = info
    }
}