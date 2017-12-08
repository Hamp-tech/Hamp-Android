package com.hamp.mvvm.start

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.hamp.R
import com.hamp.common.BaseActivity
import com.hamp.di.Injectable
import com.hamp.mvvm.extensions.observe
import com.hamp.mvvm.login.LoginActivity
import com.hamp.mvvm.signup.SignUpActivity
import com.hamp.mvvm.start.StartAppState.FIRST_TIME
import com.hamp.mvvm.start.StartAppState.LOGIN
import com.hamp.mvvm.tutorial.TutorialActivity
import kotlinx.android.synthetic.main.activity_start.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import javax.inject.Inject

class StartActivity : BaseActivity(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit private var startViewModel: StartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpViewModel()

        setContentView(R.layout.activity_start)

        startButton.onClick { goSignUp() }
        alreadyHaveAccount.onClick { goLogin() }
        login.onClick { goLogin() }
    }

    private fun setUpViewModel() {
        startViewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(StartViewModel::class.java)

        startViewModel.state.observe(this, {
            if (it == FIRST_TIME) goTutorial()
            else if (it == LOGIN) goHome()
        })
    }

    private fun goTutorial() {
        startActivity<TutorialActivity>()
        finish()
    }

    private fun goHome() {
        startActivity<TutorialActivity>()
        finish()
    }

    private fun goLogin() = startActivity<LoginActivity>()

    private fun goSignUp() = startActivity<SignUpActivity>()
}
