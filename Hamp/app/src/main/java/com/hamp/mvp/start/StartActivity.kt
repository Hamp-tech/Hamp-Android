package com.hamp.mvp.start

import android.os.Bundle
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.mvp.login.LoginActivity
import kotlinx.android.synthetic.main.activity_start.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class StartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        startButton.onClick { goSignUp() }
        alreadyHaveAccount.onClick { goLogin() }
        login.onClick { goLogin() }
    }

    private fun goLogin() {
        startActivity<LoginActivity>()
    }

    private fun goSignUp() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
