package com.erebor.tomkins.pos.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.erebor.tomkins.pos.TomkinApps
import com.erebor.tomkins.pos.di.AppComponent


abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: B
    abstract val layout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val mainApplication = application as TomkinApps
//        inject(mainApplication.appComponent)
        binding = DataBindingUtil.setContentView(this, layout)
    }

    abstract fun inject(appComponent: AppComponent)


    protected fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        val supportActionBar = supportActionBar
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(true)
            supportActionBar.setDisplayHomeAsUpEnabled(true)
            supportActionBar.setDisplayShowHomeEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
