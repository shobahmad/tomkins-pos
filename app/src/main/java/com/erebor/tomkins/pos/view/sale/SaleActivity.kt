package com.erebor.tomkins.pos.view.sale

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

import com.erebor.tomkins.pos.R
import com.erebor.tomkins.pos.base.BaseActivity
import com.erebor.tomkins.pos.data.ui.SaleHistoryUiModel
import com.erebor.tomkins.pos.data.ui.SaleUiModel
import com.erebor.tomkins.pos.databinding.ActivitySaleBinding
import com.erebor.tomkins.pos.di.AppComponent
import com.erebor.tomkins.pos.view.callback.ItemQtyHandler
import com.erebor.tomkins.pos.view.dashboard.DashboardActivity
import com.erebor.tomkins.pos.view.scan.ScannerActivity

import java.util.ArrayList

class SaleActivity : BaseActivity<ActivitySaleBinding>() {

    private var saleHistoryAdapter: SaleHistoryAdapter? = null

    override val layout: Int
        get() = R.layout.activity_sale

    override fun inject(appComponent: AppComponent) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setToolbar(binding.toolbar.toolbar)
        fetchProductInfo(intent.getStringExtra("data"))

        saleHistoryAdapter = SaleHistoryAdapter(this)
        binding.recyclerHistory.layoutManager = LinearLayoutManager(this)
        binding.recyclerHistory.adapter = saleHistoryAdapter

        binding.handler = object : ItemQtyHandler {
            override fun onPositiveButtonClick(item: View) {
                val saleUiModel = binding.sale!!
                binding.sale = saleUiModel.copy(qty = saleUiModel.qty + 1)
            }

            override fun onNegativeButtonClick(item: View) {
                var saleUiModel = binding.sale!!
                binding.sale = saleUiModel.copy(qty = saleUiModel.qty - 1)
            }
        }

        binding.buttonConfirm.setOnClickListener { v ->
            binding.confirmed = true
            fetchHistory()
        }
        binding.buttonScan.setOnClickListener { v -> startActivityForResult(Intent(this@SaleActivity, ScannerActivity::class.java), 1) }
    }

    private fun fetchProductInfo(productId: String) {
        binding.sale = SaleUiModel(productId, "Falcon All Black", "42", 316000.0, 1)
        binding.confirmed = false
    }

    private fun fetchHistory() {
        val historyUiModelList = ArrayList<SaleHistoryUiModel>()
        for (i in 1..20) {
            if (i % 4 == 0) {
                historyUiModelList.add(SaleHistoryUiModel("QRCODE000$i", "9 Nov 2019 14:32", "Product not found!"))
                continue
            }
            historyUiModelList.add(SaleHistoryUiModel("QRCODE000$i", "9 Nov 2019 14:32", "Falcon All Black $i", (if (i % 3 == 0) 2 else 1), "42", (if (i % 3 == 0) 319000.0 else 402000.0)))
        }
        saleHistoryAdapter?.addList(historyUiModelList)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val qrcode = data!!.getStringExtra("data")
            fetchProductInfo(qrcode)
        }
    }
}
