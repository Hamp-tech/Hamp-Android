package com.hamp.di.module

import com.hamp.mvvm.login.LoginActivity
import com.hamp.mvvm.signup.SignUpActivity
import com.hamp.mvvm.start.StartActivity
import com.hamp.mvvm.tutorial.TutorialActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeUserStartActivity(): StartActivity

    @ContributesAndroidInjector
    internal abstract fun contributeUserTutorialActivity(): TutorialActivity

    @ContributesAndroidInjector
    internal abstract fun contributeUserSignUpActivity(): SignUpActivity

    @ContributesAndroidInjector
    internal abstract fun contributeUserSignLoginActivity(): LoginActivity

}