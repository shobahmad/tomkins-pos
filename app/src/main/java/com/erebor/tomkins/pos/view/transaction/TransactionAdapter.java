package com.erebor.tomkins.pos.view.transaction;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseAdapter;
import com.erebor.tomkins.pos.data.ui.TransactionDetailUiModel;
import com.erebor.tomkins.pos.databinding.ItemTransactionBinding;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

public class TransactionAdapter extends BaseAdapter<ItemTransactionBinding, TransactionDetailUiModel> {

    public TransactionAdapter(Context context) {
        super(context);
    }

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
        binding.textNote.setStartIconOnClickListener(null);

        binding.textTransId.setTextColor(getContext().getResources().getColor(binding.getTransaction().getQty() < 0 ?  R.color.watermelon : R.color.colorPrimary));
        binding.textSubTotal.setTextColor(getContext().getResources().getColor(binding.getTransaction().getQty() < 0 ?  R.color.watermelon : R.color.colorPrimary));

        Drawable transType = getContext().getResources().getDrawable(binding.getTransaction().getQty() < 0 ? R.drawable.ic_transaction_return : R.drawable.ic_transaction_sale);
        transType.setBounds(0, 0, 60, 60);
        binding.textTransId.setCompoundDrawables(transType, null, null, null);

        Drawable transStatus = getContext().getResources().getDrawable(binding.getTransaction().isUploaded() ? R.drawable.ic_done : R.drawable.ic_time);
        transStatus.setBounds(0, 0, 60, 60);
        binding.textTransTime.setCompoundDrawables(null, null, transStatus, null);
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


}
