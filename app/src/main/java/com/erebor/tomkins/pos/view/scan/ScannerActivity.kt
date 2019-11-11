package com.erebor.tomkins.pos.view.scan

import android.app.Activity
import android.content.Intent
import android.os.Bundle

import com.erebor.tomkins.pos.R
import com.erebor.tomkins.pos.base.BaseActivity
import com.erebor.tomkins.pos.databinding.ActivityScannerBinding
import com.erebor.tomkins.pos.di.AppComponent
import com.erebor.tomkins.pos.helper.CheckPermissionHelper
import com.google.zxing.Result

import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScannerActivity : BaseActivity<ActivityScannerBinding>(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null

    override val layout: Int
        get() = R.layout.activity_scanner

    override fun inject(appComponent: AppComponent) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbar(binding.toolbar)
        mScannerView = ZXingScannerView(this)
        binding.contentFrame.addView(mScannerView)

        val checkPermissionHelper = CheckPermissionHelper(this)
        if (!checkPermissionHelper.checkCameraPermission())
            checkPermissionHelper.showCameraPermission(1)

        binding.layoutFlashMode.setOnClickListener { v ->
            binding.flash = !binding.flash!!
            mScannerView!!.flash = !mScannerView!!.flash
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            onResume()
        }
    }

    override fun handleResult(result: Result) {
        val intent = Intent()
        intent.putExtra("data", result.text)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this)
        mScannerView!!.startCamera()
    }

    override fun onPause() {
        super.onPause()
        mScannerView!!.stopCamera()
    }

    override fun onStop() {
        super.onStop()
        mScannerView!!.stopCamera()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mScannerView!!.stopCamera()
    }
}
