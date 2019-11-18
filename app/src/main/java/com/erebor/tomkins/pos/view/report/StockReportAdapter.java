package com.erebor.tomkins.pos.view.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.erebor.tomkins.pos.base.BaseAdapter;
import com.erebor.tomkins.pos.data.ui.StockUiModel;
import com.erebor.tomkins.pos.databinding.ItemStockBinding;

public class StockReportAdapter extends BaseAdapter<ItemStockBinding, StockUiModel> {

    public StockReportAdapter(Context context) {
        super(context);
    }

    @Override
    public void setDataBinding(ViewHolder viewHolder, StockUiModel data) {
        ItemStockBinding binding = (ItemStockBinding) viewHolder.getBinding();
        binding.setStock(data);
    }

    @Override
    public ItemStockBinding inflate(LayoutInflater layoutInflater, ViewGroup parent) {
        return ItemStockBinding.inflate(layoutInflater, parent, false);
    }

    @Override
    public boolean areItemsTheSame(StockUiModel oldItem, StockUiModel newItem) {
        return oldItem.getProductId().equals(newItem.getProductId());
    }

    @Override
    public StockUiModel getItem(ViewHolder viewHolder) {
        ItemStockBinding binding = (ItemStockBinding) viewHolder.getBinding();
        return binding.getStock();
    }
}
