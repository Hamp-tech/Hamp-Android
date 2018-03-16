package com.hamp.di.module

import com.hamp.mvvm.home.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributeUserProfileFragment(): ProfileFragment

}