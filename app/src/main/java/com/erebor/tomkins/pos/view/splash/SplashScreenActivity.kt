package com.erebor.tomkins.pos.view.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

import com.erebor.tomkins.pos.R
import com.erebor.tomkins.pos.base.BaseActivity
import com.erebor.tomkins.pos.databinding.ActivitySplashBinding
import com.erebor.tomkins.pos.di.AppComponent
import com.erebor.tomkins.pos.view.dashboard.DashboardActivity
import com.erebor.tomkins.pos.view.login.LoginActivity
import com.erebor.tomkins.pos.viewmodel.splash.SplashViewModel
import com.erebor.tomkins.pos.viewmodel.splash.SplashViewState

import javax.inject.Inject

class SplashScreenActivity : BaseActivity<ActivitySplashBinding>() {

//    @Inject
    var viewModelFactory: ViewModelProvider.Factory? = null

    var splashViewModel: SplashViewModel? = null

    override val layout: Int
        get() = R.layout.activity_splash

    override fun inject(appComponent: AppComponent) {
        appComponent.doInjection(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        splashViewModel = ViewModelProviders.of(this, viewModelFactory).get(SplashViewModel::class.java!!)
        observeDataChange()
        splashViewModel!!.sessionCheck()
    }

    private fun observeDataChange() {
        splashViewModel!!.viewState.observe(this, Observer { splashViewState ->
            if (splashViewState.currentState == SplashViewState.LOADING_STATE.currentState) {
                binding.setProgress(splashViewState.progress)
                return@Observer
            }
            if (splashViewState.currentState == SplashViewState.SESSION_EMPTY_STATE.currentState) {
                binding.setProgress(splashViewState.progress)
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return@Observer
            }
            if (splashViewState.currentState == SplashViewState.SESSION_VALID_STATE.currentState) {
                binding.setProgress(splashViewState.progress)
                startActivity(Intent(this, DashboardActivity::class.java))
                finish()
                return@Observer
            }
            if (splashViewState.currentState == SplashViewState.ERROR_STATE.currentState) {
                binding.setProgress(splashViewState.progress)
                Toast.makeText(this, splashViewState.error.message, Toast.LENGTH_LONG).show()
                return@Observer
            }
        })
    }
}
