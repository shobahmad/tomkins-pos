package com.erebor.tomkins.pos.view.login

import android.content.Intent
import android.os.Bundle

import com.erebor.tomkins.pos.R
import com.erebor.tomkins.pos.base.BaseActivity
import com.erebor.tomkins.pos.databinding.ActivityLoginBinding
import com.erebor.tomkins.pos.di.AppComponent
import com.erebor.tomkins.pos.view.dashboard.DashboardActivity

class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    override val layout: Int
        get() = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.buttonLogin.setOnClickListener { v ->
            binding.loading = true
            binding.buttonLogin.postDelayed({
                binding.loading = false
                startActivity(Intent(this@LoginActivity, DashboardActivity::class.java))
                finish()
            }, 2000)
        }
    }

    override fun inject(appComponent: AppComponent) {

    }
}
