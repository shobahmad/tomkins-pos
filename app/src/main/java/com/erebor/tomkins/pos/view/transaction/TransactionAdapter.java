package com.erebor.tomkins.pos.view.transaction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.erebor.tomkins.pos.base.BaseAdapter;
import com.erebor.tomkins.pos.data.ui.TransactionDetailUiModel;
import com.erebor.tomkins.pos.databinding.ItemTransactionBinding;
import com.erebor.tomkins.pos.view.callback.ItemQtyHandler;

public class TransactionAdapter extends BaseAdapter<ItemTransactionBinding, TransactionDetailUiModel> {

    public TransactionAdapter(Context context) {
        super(context);
    }
    private ItemUpdatedListener itemUpdatedListener;

    @Override
    public void setDataBinding(ViewHolder viewHolder, TransactionDetailUiModel data) {
        ItemTransactionBinding binding = (ItemTransactionBinding) viewHolder.getBinding();
        binding.setTransaction(data);
    }

    @Override
    public ItemTransactionBinding inflate(LayoutInflater layoutInflater, ViewGroup parent) {
        return ItemTransactionBinding.inflate(layoutInflater, parent, false);
    }

    @Override
    public boolean areItemsTheSame(TransactionDetailUiModel oldItem, TransactionDetailUiModel newItem) {
        return oldItem.getIndTrx() == newItem.getIndTrx();
    }

    @Override
    public TransactionDetailUiModel getItem(ViewHolder viewHolder) {
        ItemTransactionBinding binding = (ItemTransactionBinding) viewHolder.getBinding();
        return binding.getTransaction();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        ItemTransactionBinding binding = (ItemTransactionBinding) holder.getBinding();
        binding.setHandler(new ItemQtyHandler() {
            TransactionDetailUiModel transaction = binding.getTransaction();
            @Override
            public void onPositiveButtonClick(View item) {

                if (itemUpdatedListener != null) {
                    itemUpdatedListener.qtyUpdate(transaction.getBarcode(), transaction.getQty() + 1);
                    return;
                }
                setItem(position, new TransactionDetailUiModel(
                        transaction.getIndTrx(),
                        transaction.getArtName(),
                        transaction.getArtCode(),
                        transaction.getBarcode(),
                        transaction.getSize(),
                        transaction.getHargaNormal(),
                        transaction.getEventName(),
                        transaction.getQty() + 1,
                        transaction.getHargaJual(),
                        transaction.getNote()
                ));
                notifyItemChanged(position);
            }

            @Override
            public void onNegativeButtonClick(View item) {
                if (transaction.getQty() == 1 && itemUpdatedListener != null) {
                    itemUpdatedListener.qtyUpdate(transaction.getBarcode(), 0);
                    return;
                }
                if (transaction.getQty() == 1) {
                    removeItem(getList().get(position));
                    return;
                }

                if (itemUpdatedListener != null) {
                    itemUpdatedListener.qtyUpdate(transaction.getBarcode(), transaction.getQty() - 1);
                    return;
                }
                setItem(position, new TransactionDetailUiModel(
                        transaction.getIndTrx(),
                        transaction.getArtName(),
                        transaction.getArtCode(),
                        transaction.getBarcode(),
                        transaction.getSize(),
                        transaction.getHargaNormal(),
                        transaction.getEventName(),
                        transaction.getQty() - 1,
                        transaction.getHargaJual(),
                        transaction.getNote()
                ));
                notifyItemChanged(position);

            }
        });
    }

    public void setItemUpdatedListener(ItemUpdatedListener itemUpdatedListener) {
        this.itemUpdatedListener = itemUpdatedListener;
    }

    public interface ItemUpdatedListener {
        void qtyUpdate(String barcode, int qty);
    }
}
