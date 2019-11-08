package com.erebor.tomkins.pos.view.sale;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.erebor.tomkins.pos.base.BaseAdapter;
import com.erebor.tomkins.pos.data.ui.SaleHistoryUiModel;
import com.erebor.tomkins.pos.databinding.ItemSaleHistoryBinding;

public class SaleHistoryAdapter extends BaseAdapter<ItemSaleHistoryBinding, SaleHistoryUiModel> {
    public SaleHistoryAdapter(Context context) {
        super(context);
    }

    @Override
    public void setDataBinding(ViewHolder viewHolder, SaleHistoryUiModel data) {
        ItemSaleHistoryBinding binding = (ItemSaleHistoryBinding) viewHolder.getBinding();
        binding.setHistory(data);
    }

    @Override
    public ItemSaleHistoryBinding inflate(LayoutInflater layoutInflater, ViewGroup parent) {
        return ItemSaleHistoryBinding.inflate(layoutInflater, parent, false);
    }

    @Override
    public boolean areItemsTheSame(SaleHistoryUiModel oldItem, SaleHistoryUiModel newItem) {
        return oldItem.getId().equals(newItem.getId());
    }

    @Override
    public SaleHistoryUiModel getItem(ViewHolder viewHolder) {
        ItemSaleHistoryBinding binding = (ItemSaleHistoryBinding) viewHolder.getBinding();
        return binding.getHistory();
    }
}
