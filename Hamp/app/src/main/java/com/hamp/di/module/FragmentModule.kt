package com.hamp.di.module

import com.hamp.mvvm.home.profile.ProfileFragment
import com.hamp.mvvm.home.service.ServiceFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributeServiceServiceFragment(): ServiceFragment

    @ContributesAndroidInjector
    internal abstract fun contributeUserProfileFragment(): ProfileFragment

}