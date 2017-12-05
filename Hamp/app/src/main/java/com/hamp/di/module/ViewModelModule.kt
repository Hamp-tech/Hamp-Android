package com.hamp.di.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.hamp.di.ViewModelKey
import com.hamp.mvvm.signup.SignUpViewModel
import com.hamp.viewmodel.HampViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(usersViewModel: SignUpViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: HampViewModelFactory): ViewModelProvider.Factory
}