package com.erebor.tomkins.pos.view.sync;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.erebor.tomkins.pos.base.BaseAdapter;
import com.erebor.tomkins.pos.data.ui.DownloadUiModel;
import com.erebor.tomkins.pos.databinding.ItemDownloadInfoBinding;

public class DownloadInfoAdapter extends BaseAdapter<ItemDownloadInfoBinding, DownloadUiModel> {
    public DownloadInfoAdapter(Context context) {
        super(context);
    }

    @Override
    public void setDataBinding(ViewHolder viewHolder, DownloadUiModel data) {
        ItemDownloadInfoBinding binding = (ItemDownloadInfoBinding) viewHolder.getBinding();
        binding.setDownload(data);
    }

    @Override
    public ItemDownloadInfoBinding inflate(LayoutInflater layoutInflater, ViewGroup parent) {
        return ItemDownloadInfoBinding.inflate(layoutInflater, parent, false);
    }

    @Override
    public boolean areItemsTheSame(DownloadUiModel oldItem, DownloadUiModel newItem) {
        return oldItem.getTitle().equals(newItem.getTitle());
    }

    @Override
    public DownloadUiModel getItem(ViewHolder viewHolder) {
        ItemDownloadInfoBinding binding = (ItemDownloadInfoBinding) viewHolder.getBinding();
        return binding.getDownload();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemDownloadInfoBinding binding = (ItemDownloadInfoBinding) holder.getBinding();
        binding.snippetDownload.imageArrowRight.setVisibility(View.GONE);
    }
}
