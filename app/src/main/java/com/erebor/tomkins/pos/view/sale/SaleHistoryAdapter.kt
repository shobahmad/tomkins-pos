package com.erebor.tomkins.pos.view.sale

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup

import com.erebor.tomkins.pos.base.BaseAdapter
import com.erebor.tomkins.pos.data.ui.SaleHistoryUiModel
import com.erebor.tomkins.pos.databinding.ItemSaleHistoryBinding

class SaleHistoryAdapter(context: Context) : BaseAdapter<ItemSaleHistoryBinding, SaleHistoryUiModel>(context) {
    override fun setDataBinding(viewHolder: ViewHolder<ItemSaleHistoryBinding>, data: SaleHistoryUiModel) {
        val binding = viewHolder.binding as ItemSaleHistoryBinding
        binding.history = data
    }

    override fun inflate(layoutInflater: LayoutInflater, parent: ViewGroup): ItemSaleHistoryBinding {
        return ItemSaleHistoryBinding.inflate(layoutInflater, parent, false)
    }

    override fun areItemsTheSame(oldItem: SaleHistoryUiModel, newItem: SaleHistoryUiModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun getItem(viewHolder: ViewHolder<ItemSaleHistoryBinding>): SaleHistoryUiModel {
        return binding.history!!
    }
}
