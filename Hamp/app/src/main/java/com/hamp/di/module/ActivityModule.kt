package com.hamp.di.module

import com.hamp.mvvm.signup.SignUpActivity
import com.hamp.mvvm.start.StartActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    
    @ContributesAndroidInjector
    internal abstract fun contributeUserStartActivity(): StartActivity

    @ContributesAndroidInjector
    internal abstract fun contributeUserSignUpActivity(): SignUpActivity

}