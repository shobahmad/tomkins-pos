package com.erebor.tomkins.pos.view.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.erebor.tomkins.pos.base.BaseAdapter;
import com.erebor.tomkins.pos.data.local.model.StockReportModel;
import com.erebor.tomkins.pos.databinding.ItemStockBinding;
import com.erebor.tomkins.pos.helper.DateConverterHelper;

public class StockReportAdapter extends BaseAdapter<ItemStockBinding, StockReportModel> {

    private final DateConverterHelper dateConverterHelper;
    public StockReportAdapter(Context context, DateConverterHelper dateConverterHelper) {
        super(context);
        this.dateConverterHelper = dateConverterHelper;
    }

    @Override
    public void setDataBinding(ViewHolder viewHolder, StockReportModel data) {
        ItemStockBinding binding = (ItemStockBinding) viewHolder.getBinding();
        binding.setFormattedLastUpdate(dateConverterHelper.getDifference(data.getLastUpdate().getTime()));
        binding.setStock(data);
    }

    @Override
    public ItemStockBinding inflate(LayoutInflater layoutInflater, ViewGroup parent) {
        return ItemStockBinding.inflate(layoutInflater, parent, false);
    }

    @Override
    public boolean areItemsTheSame(StockReportModel oldItem, StockReportModel newItem) {
        return oldItem.getNoBarcode().equals(newItem.getNoBarcode());
    }

    @Override
    public StockReportModel getItem(ViewHolder viewHolder) {
        ItemStockBinding binding = (ItemStockBinding) viewHolder.getBinding();
        return binding.getStock();
    }
}
