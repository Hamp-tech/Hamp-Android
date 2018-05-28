package com.hamp.mvvm.start

import android.os.Bundle
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.mvvm.login.LoginActivity
import com.hamp.mvvm.signup.SignUpActivity
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

    private fun goLogin() = startActivity<LoginActivity>()

    private fun goSignUp() = startActivity<SignUpActivity>()
}
