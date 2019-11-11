package com.erebor.tomkins.pos.di

import com.erebor.tomkins.pos.viewmodel.splash.SplashViewModel

import dagger.Subcomponent

/**
 * A sub component to create ViewModels. It is called by the
 * [com.erebor.tomkins.pos.factory.ViewModelFactory].
 */
@Subcomponent
interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }

    fun splashViewModel(): SplashViewModel

}
