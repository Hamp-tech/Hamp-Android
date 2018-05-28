package com.hamp.di.module

import com.hamp.mvvm.basket.BasketActivity
import com.hamp.mvvm.card.CardActivity
import com.hamp.mvvm.login.LoginActivity
import com.hamp.mvvm.paymentMethod.PaymentMethodActivity
import com.hamp.mvvm.signup.SignUpActivity
import com.hamp.mvvm.splash.SplashActivity
import com.hamp.mvvm.start.StartActivity
import com.hamp.mvvm.tutorial.TutorialActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributePrefsSplashActivity(): SplashActivity

    @ContributesAndroidInjector
    internal abstract fun contributePrefsTutorialActivity(): TutorialActivity

    @ContributesAndroidInjector
    internal abstract fun contributeUserSignUpActivity(): SignUpActivity

    @ContributesAndroidInjector
    internal abstract fun contributeUserSignLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    internal abstract fun contributeServicesBasketActivity(): BasketActivity

    @ContributesAndroidInjector
    internal abstract fun contributeCardPaymentMethodActivity(): PaymentMethodActivity

    @ContributesAndroidInjector
    internal abstract fun contributeCardCardActivity(): CardActivity

}