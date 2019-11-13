package com.erebor.tomkins.pos.view.sync;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.ui.DownloadUiModel;
import com.erebor.tomkins.pos.databinding.ActivitySyncBinding;
import com.erebor.tomkins.pos.di.AppComponent;

public class SyncActivity extends BaseActivity<ActivitySyncBinding> {
    @Override
    public void inject(AppComponent appComponent) {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_sync;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setToolbar(binding.toolbar.toolbar);
        binding.setTitle(getResources().getString(R.string.dashboard_data_sync));

        binding.setDownloadGoods(new DownloadUiModel("Download Goods"));
        binding.setDownloadDelivery(new DownloadUiModel("Download Delivery Order"));
        binding.setDownloadDiscount(new DownloadUiModel("Download Discount"));
        binding.setDownloadGender(new DownloadUiModel("Download Gender"));
        binding.setDownloadOpname(new DownloadUiModel("Download Opname"));
        binding.setDownloadPrice(new DownloadUiModel("Download Price"));
    }
}
