package com.erebor.tomkins.pos.factory

import androidx.collection.ArrayMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.erebor.tomkins.pos.di.ViewModelSubComponent
import com.erebor.tomkins.pos.viewmodel.splash.SplashViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject
constructor(viewModelSubComponent: ViewModelSubComponent) : ViewModelProvider.Factory {
    private val creators: ArrayMap<Class<*>, ViewModel>

    init {
        creators = ArrayMap()

        creators[SplashViewModel::class.java] = viewModelSubComponent.splashViewModel()

    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        var creator = creators[modelClass]
        if (creator == null) {
            for ((key, value) in creators) {
                if (modelClass.isAssignableFrom(key)) {
                    creator = value
                    break
                }
            }
        }
        kotlin.requireNotNull(creator) { "Unknown model class $modelClass" }
        try {
//            return creator!!.call() as T
            return create(modelClass)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}

