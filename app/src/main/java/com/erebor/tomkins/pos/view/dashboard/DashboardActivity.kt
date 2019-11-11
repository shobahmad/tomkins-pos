package com.erebor.tomkins.pos.view.dashboard

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast

import com.erebor.tomkins.pos.R
import com.erebor.tomkins.pos.base.BaseActivity
import com.erebor.tomkins.pos.data.ui.DownloadUiModel
import com.erebor.tomkins.pos.data.ui.ReportSummaryUiModel
import com.erebor.tomkins.pos.data.ui.UserUiModel
import com.erebor.tomkins.pos.databinding.ActivityDashboardBinding
import com.erebor.tomkins.pos.databinding.ActivityMainBinding
import com.erebor.tomkins.pos.di.AppComponent
import com.erebor.tomkins.pos.view.callback.IItemClick
import com.erebor.tomkins.pos.view.sale.SaleActivity
import com.erebor.tomkins.pos.view.scan.ScannerActivity
import com.erebor.tomkins.pos.view.setting.SettingActivity

class DashboardActivity : BaseActivity<ActivityDashboardBinding>() {

    internal var downloadState = 0

    override val layout: Int
        get() = R.layout.activity_dashboard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchDummyData()
        binding.buttonScan.setOnClickListener { v -> startActivityForResult(Intent(this@DashboardActivity, ScannerActivity::class.java), 1) }
        //TODO
//        binding.setSettingClick { item -> startActivity(Intent(this@DashboardActivity, SettingActivity::class.java)) }
    }

    private fun startSaleActivity(productId: String) {
        val intent = Intent(this@DashboardActivity, SaleActivity::class.java)
        intent.putExtra("data", productId)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val qrcode = data!!.getStringExtra("data")
            fetchBarcode(qrcode)
        }
    }

    private fun fetchBarcode(qrcode: String) {
        startSaleActivity(qrcode)
    }

    private fun fetchDummyData() {
        binding.user = UserUiModel(1, "frodo", "Frodo Bagins", "Hobbits")


        binding.layoutDownloadInfo.setOnClickListener { v ->
            if (downloadState == 0) {
                downloadState = 1
                binding.download = DownloadUiModel(true, 40, "Downloading products...")

                binding.layoutDownloadInfo.postDelayed({
                    binding.download = DownloadUiModel(true, 99, "Downloading price...")
                }, 1000)

                binding.layoutDownloadInfo.postDelayed({
                    downloadState = 0
                    binding.download = DownloadUiModel(false, "3 minutes ago")
                }, 2000)

//                return@binding.layoutDownloadInfo.setOnClickListener
                return@setOnClickListener
            }

            downloadState = 0
            binding.download = DownloadUiModel(false, "3 minutes ago")

        }

        downloadState = 0
        binding.download = DownloadUiModel(false, "3 minutes ago")
        binding.summary = ReportSummaryUiModel(120, 100, 82)
    }

    override fun inject(appComponent: AppComponent) {

    }
}
