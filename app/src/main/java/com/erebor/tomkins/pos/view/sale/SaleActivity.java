package com.erebor.tomkins.pos.view.sale;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.ui.SaleHistoryUiModel;
import com.erebor.tomkins.pos.data.ui.SaleUiModel;
import com.erebor.tomkins.pos.databinding.ActivitySaleBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.tools.SharedPrefs;
import com.erebor.tomkins.pos.view.callback.ItemQtyHandler;
import com.erebor.tomkins.pos.view.dashboard.DashboardActivity;
import com.erebor.tomkins.pos.view.scan.VisionScannerActivity;
import com.erebor.tomkins.pos.view.scan.ZynxScannerActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SaleActivity extends BaseActivity<ActivitySaleBinding> {

    SaleHistoryAdapter saleHistoryAdapter;
    @Inject
    SharedPrefs sharedPrefs;

    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_sale;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar(binding.toolbar.toolbar);
        fetchProductInfo(getIntent().getStringExtra("data"));

        saleHistoryAdapter = new SaleHistoryAdapter(this);
        binding.recyclerHistory.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerHistory.setAdapter(saleHistoryAdapter);

        binding.setHandler(new ItemQtyHandler() {
            @Override
            public void onPositiveButtonClick(View item) {
                SaleUiModel saleUiModel = binding.getSale();
                saleUiModel.setQty(saleUiModel.getQty() + 1);
                binding.setSale(saleUiModel);
            }

            @Override
            public void onNegativeButtonClick(View item) {
                SaleUiModel saleUiModel = binding.getSale();
                saleUiModel.setQty(saleUiModel.getQty() - 1);
                binding.setSale(saleUiModel);
            }
        });

        binding.buttonConfirm.setOnClickListener(v -> {
            binding.setConfirmed(true);
            fetchHistory();
        });
        binding.buttonScan.setOnClickListener(v -> {

            String scanner = sharedPrefs.getString(getResources().getString(R.string.setting_key_camera), "");
            if (scanner.equals("")) {
                startActivityForResult(new Intent(this, ZynxScannerActivity.class), 1);
                return;
            }

            if (scanner.equals("zxing")) {
                startActivityForResult(new Intent(this, ZynxScannerActivity.class), 1);
                return;
            }

            startActivityForResult(new Intent(this, VisionScannerActivity.class), 1);
        });
    }

    private void fetchProductInfo(String productId) {
        SaleUiModel saleUiModel = new SaleUiModel();
        saleUiModel.setProductId(productId);
        saleUiModel.setName("Falcon All Black");
        saleUiModel.setDescription("Example description dummy");
        saleUiModel.setPrice(316000d);
        saleUiModel.setQty(1);
        saleUiModel.setSize("42");
        saleUiModel.setImage("https://www.tomkins.id/wp-content/uploads/2019/10/Jojo-BP-1.jpg");

        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));

        Glide.with(this)
                .load(saleUiModel.getImage())
                .apply(requestOptions)
                .into(binding.imageProduct);

        binding.setSale(saleUiModel);
        binding.setConfirmed(false);
    }

    private void fetchHistory() {
        List<SaleHistoryUiModel> historyUiModelList = new ArrayList<SaleHistoryUiModel>();
        for (int i = 1; i < 21; i++) {
            SaleHistoryUiModel saleHistoryUiModel = new SaleHistoryUiModel();
            saleHistoryUiModel.setId("QRCODE000"+i);
            if (i % 4 == 0) {
                saleHistoryUiModel.setSuccess(false);
                saleHistoryUiModel.setDate("9 Nov 2019 14:32");
                saleHistoryUiModel.setErrorMessage("Product not found!");
                historyUiModelList.add(saleHistoryUiModel);
                continue;
            }
            saleHistoryUiModel.setSuccess(true);
            saleHistoryUiModel.setName("Falcon All Black " + i);
            saleHistoryUiModel.setQty(i % 3 == 0 ? 2 : 1);
            saleHistoryUiModel.setDate("9 Nov 2019 14:32");
            saleHistoryUiModel.setPrice(i % 3 == 0 ? 319000d : 402000d);
            saleHistoryUiModel.setSize("42");
            historyUiModelList.add(saleHistoryUiModel);
        }
        saleHistoryAdapter.addList(historyUiModelList);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String qrcode = data.getStringExtra("data");
            fetchProductInfo(qrcode);
        }
    }
}
