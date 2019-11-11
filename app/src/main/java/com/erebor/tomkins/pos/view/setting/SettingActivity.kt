package com.erebor.tomkins.pos.view.setting

import android.os.Bundle
import androidx.fragment.app.FragmentManager

import com.erebor.tomkins.pos.R
import com.erebor.tomkins.pos.base.BaseActivity
import com.erebor.tomkins.pos.databinding.ActivitySettingBinding
import com.erebor.tomkins.pos.di.AppComponent

class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    override fun inject(appComponent: AppComponent) {}
    override val layout: Int
        get() = R.layout.activity_setting

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction()
                .replace(R.id.frameLayout, SettingPrefsFragment(), "SettingPrefsFragment")
                .commit()
    }
}
