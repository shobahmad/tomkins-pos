package com.erebor.tomkins.pos.view.receive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.erebor.tomkins.pos.base.BaseAdapter;
import com.erebor.tomkins.pos.data.ui.ProductReceiveUiModel;
import com.erebor.tomkins.pos.databinding.ItemArticleBinding;
import com.erebor.tomkins.pos.databinding.ItemProductReceiveBinding;

public class ProductReceiveAdapter extends BaseAdapter<ItemProductReceiveBinding, ProductReceiveUiModel> {
    public ProductReceiveAdapter(Context context) {
        super(context);
    }

    @Override
    public void setDataBinding(ViewHolder viewHolder, ProductReceiveUiModel data) {
        ItemProductReceiveBinding binding = (ItemProductReceiveBinding) viewHolder.getBinding();
        binding.setReceive(data);
    }

    @Override
    public ItemProductReceiveBinding inflate(LayoutInflater layoutInflater, ViewGroup parent) {
        return ItemProductReceiveBinding.inflate(layoutInflater, parent, false);
    }

    @Override
    public boolean areItemsTheSame(ProductReceiveUiModel oldItem, ProductReceiveUiModel newItem) {
        return oldItem.getNoDo().equals(newItem.getNoDo());
    }

    @Override
    public ProductReceiveUiModel getItem(ViewHolder viewHolder) {
        ItemProductReceiveBinding binding = (ItemProductReceiveBinding) viewHolder.getBinding();
        return binding.getReceive();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        ItemProductReceiveBinding binding = (ItemProductReceiveBinding) holder.getBinding();
        Double percent = binding.getReceive().getQtyReceived() == 0 ? 0 : binding.getReceive().getQtyReceived() / binding.getReceive().getQtyTotal() * 100;
        binding.setProgress(percent.intValue());
    }
}
