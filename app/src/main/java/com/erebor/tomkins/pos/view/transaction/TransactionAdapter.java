package com.erebor.tomkins.pos.view.transaction;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;

import com.erebor.tomkins.pos.base.BaseAdapter;
import com.erebor.tomkins.pos.data.ui.TransactionDetailUiModel;
import com.erebor.tomkins.pos.databinding.ItemTransactionBinding;
import com.erebor.tomkins.pos.view.callback.ItemQtyHandler;

public class TransactionAdapter extends BaseAdapter<ItemTransactionBinding, TransactionDetailUiModel> {

    final Handler handler;
    private TextWatcher textWatcher = null;

    public TransactionAdapter(Context context) {
        super(context);
        handler = new Handler(msg -> {
            if (msg.what == 1) {
                String barcode = msg.getData().getString("barcode");
                int qty = msg.getData().getInt("qty");
                itemUpdatedListener.qtyUpdate(barcode, qty);
                return true;
            }

            if (msg.what == 2) {
                String barcode = msg.getData().getString("barcode");
                String note = msg.getData().getString("note");
                itemUpdatedListener.noteUpdate(barcode, note);
            }

            return false;
        });
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
                    sendUpdateQty(transaction.getBarcode(), transaction.getQty() + 1);
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
                    sendUpdateQty(transaction.getBarcode(), 0);
//                    itemUpdatedListener.qtyUpdate(transaction.getBarcode(), 0);
                    return;
                }
                if (transaction.getQty() == 1) {
                    removeItem(getList().get(position));
                    return;
                }

                if (itemUpdatedListener != null) {
                    sendUpdateQty(transaction.getBarcode(), transaction.getQty() - 1);
//                    itemUpdatedListener.qtyUpdate(transaction.getBarcode(), transaction.getQty() - 1);
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
        if (textWatcher != null) {
            binding.editNote.removeTextChangedListener(textWatcher);
        }
        textWatcher = new TextWatcher() {
            TransactionDetailUiModel transaction = binding.getTransaction();

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (itemUpdatedListener != null) {
                    sendUpdateNote(transaction.getBarcode(), s.toString());
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
                        transaction.getQty(),
                        transaction.getHargaJual(),
                        s.toString()
                ));
                notifyItemChanged(position);
            }
        };
        binding.editNote.addTextChangedListener(textWatcher);
        binding.textNote.setStartIconOnClickListener(null);
    }

    private void sendUpdateQty(String barcode, int qty) {
        Message message = new Message();
        message.what = 1;
        Bundle bundle = new Bundle();
        bundle.putString("barcode", barcode);
        bundle.putInt("qty", qty);
        message.setData(bundle);
        handler.removeMessages(1);
        handler.sendMessageDelayed(message, 500);
    }
    private void sendUpdateNote(String barcode, String note) {
        Message message = new Message();
        message.what = 2;
        Bundle bundle = new Bundle();
        bundle.putString("barcode", barcode);
        bundle.putString("note", note);
        message.setData(bundle);
        handler.removeMessages(2);
        handler.sendMessageDelayed(message, 1000);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ViewHolder viewHolder =  super.onCreateViewHolder(parent, viewType);
        ItemTransactionBinding binding = (ItemTransactionBinding) viewHolder.getBinding();
        showSoftKeyboard(binding.editNote);
        return viewHolder;
    }
    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    public void setItemUpdatedListener(ItemUpdatedListener itemUpdatedListener) {
        this.itemUpdatedListener = itemUpdatedListener;
    }

    public interface ItemUpdatedListener {
        void qtyUpdate(String barcode, int qty);
        void noteUpdate(String barcode, String note);
    }
}
