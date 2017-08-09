package com.hamp.mvp.start

import android.content.Intent
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.mvp.home.HomeActivity
import com.hamp.mvp.login.LoginActivity
import com.hamp.mvp.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_start.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity

class StartActivity : BaseActivity() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        setContentView(R.layout.activity_start)

//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        startButton.onClick { goSignUp() }
        alreadyHaveAccount.onClick { goLogin() }
        login.onClick { goLogin() }
    }

    private fun goLogin() {
        startActivity<LoginActivity>()
    }

    private fun goSignUp() {
        startActivity<SignUpActivity>()
    }
}
