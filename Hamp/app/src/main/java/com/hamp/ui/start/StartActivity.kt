package com.hamp.ui.start

import android.content.Intent
import android.os.Bundle
import com.hamp.R
import com.hamp.auth
import com.hamp.common.BaseActivity
import com.hamp.ui.home.HomeActivity
import com.hamp.ui.login.LoginActivity
import com.hamp.ui.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_start.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class StartActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!auth.isUserLogin()) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_start)

        startButton.onClick { goSignUp() }
        alreadyHaveAccount.onClick { goLogin() }
        login.onClick { goLogin() }
    }

    private fun goLogin() = startActivity<LoginActivity>()

    private fun goSignUp() = startActivity<SignUpActivity>()
}
