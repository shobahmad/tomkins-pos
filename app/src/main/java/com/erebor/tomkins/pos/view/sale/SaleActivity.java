package com.erebor.tomkins.pos.view.sale;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.ui.SaleUiModel;
import com.erebor.tomkins.pos.databinding.ActivitySaleBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.view.callback.ItemQtyHandler;

public class SaleActivity extends BaseActivity<ActivitySaleBinding> {
    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_sale;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SaleUiModel saleUiModel = new SaleUiModel();
        saleUiModel.setProductId(getIntent().getStringExtra("data"));
        saleUiModel.setName("Pizza Orins");
        saleUiModel.setPrice(10000d);
        saleUiModel.setQty(1);
        saleUiModel.setSize("42");

        setToolbar(binding.toolbar.toolbar);

        binding.setSale(saleUiModel);
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
    }
}
