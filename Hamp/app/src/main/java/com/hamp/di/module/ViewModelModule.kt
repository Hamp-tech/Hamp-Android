package com.hamp.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.hamp.di.ViewModelKey
import com.hamp.mvvm.login.LoginViewModel
import com.hamp.mvvm.signup.SignUpViewModel
import com.hamp.mvvm.start.StartViewModel
import com.hamp.mvvm.tutorial.TutorialViewModel
import com.hamp.viewmodel.HampViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StartViewModel::class)
    abstract fun bindStartViewModel(startViewModel: StartViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TutorialViewModel::class)
    abstract fun bindTutorialViewModel(tutorialViewModel: TutorialViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(signUpViewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: HampViewModelFactory): ViewModelProvider.Factory
}