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
        binding.textNote.setStartIconOnClickListener(null);

        binding.btnDiscount.setOnClickListener(v -> inputDiscountDialog(binding.getTransaction().getArtName(), binding.getTransaction().getBarcode(), binding.getTransaction().getDiskon()));
        binding.btnPrice.setOnClickListener(v -> inputPriceDialog(binding.getTransaction().getArtName(), binding.getTransaction().getBarcode(), binding.getTransaction().getHargaJual()));

        binding.textTransId.setTextColor(getContext().getResources().getColor(binding.getTransaction().getQty() < 0 ?  R.color.watermelon : R.color.colorPrimary));
        binding.textSubTotal.setTextColor(getContext().getResources().getColor(binding.getTransaction().getQty() < 0 ?  R.color.watermelon : R.color.colorPrimary));

        Drawable img = getContext().getResources().getDrawable(binding.getTransaction().getQty() < 0 ? R.drawable.ic_transaction_return : R.drawable.ic_transaction_sale);
        img.setBounds(0, 0, 60, 60);
        binding.textTransId.setCompoundDrawables(img, null, null, null);
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
        void discountUpdate(String barcode, double discount);
        void sellingPriceUpdate(String barcode, double price);
    }

    private void inputDiscountDialog(String artikelName, final String barcode, final Double discount) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_Dialog);
        builder.setTitle(R.string.discount);
        builder.setMessage(getContext().getResources().getString(R.string.transaction_discount_input, artikelName));
        builder.setCancelable(true);

        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.dialog_input_discount, null);

        TextInputEditText editDiscount = promptsView.findViewById(R.id.editDiscount);
        Slider slider = promptsView.findViewById(R.id.sliderDiscount);

        editDiscount.setText(getContext().getResources().getString(R.string.discount_format, discount));
        editDiscount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().replaceAll("%", "").isEmpty()) {
                    return;
                }

                double value = Double.parseDouble(s.toString().replaceAll("%", ""));

                if (value < 0) {
                    editDiscount.setText(getContext().getResources().getString(R.string.discount_format, 0d));
                    slider.setValue(0f);
                    return;
                }

                if (value > 100) {
                    editDiscount.setText(getContext().getResources().getString(R.string.discount_format, 100d));
                    slider.setValue(100f);
                    return;
                }

                slider.setValue(((Number) value).floatValue());
            }
        });
        slider.setValue(discount.floatValue());
        slider.addOnChangeListener((slider1, value, fromUser) -> {
            if (!fromUser) {
                return;
            }
            editDiscount.setText(getContext().getResources().getString(R.string.discount_format, ((Number) value).doubleValue()));
        });

        builder.setView(promptsView);
        builder.setPositiveButton(getContext().getString(R.string.ok), (dialog, which) -> {
            dialog.dismiss();
            if (itemUpdatedListener == null) {
                return;
            }

            itemUpdatedListener.discountUpdate(barcode, ((Number) slider.getValue()).doubleValue());
        });
        builder.setNegativeButton(getContext().getString(R.string.cancel), ((dialog, which) -> {
            dialog.dismiss();
        }));


        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void inputPriceDialog(String artikelName, final String barcode, final Double price) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.Theme_AppCompat_Dialog);
        builder.setTitle(R.string.transaction_selling_price);
        builder.setMessage(getContext().getResources().getString(R.string.transaction_price_input, artikelName));
        builder.setCancelable(true);

        LayoutInflater li = LayoutInflater.from(getContext());
        View promptsView = li.inflate(R.layout.dialog_input_price, null);

        TextInputEditText editPrice = promptsView.findViewById(R.id.editPrice);

        editPrice.setText(getContext().getResources().getString(R.string.price_format, price));
        editPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editPrice.removeTextChangedListener(this);
                String cleanString = editPrice.getText().toString().trim().replaceAll("[$,.]", "").replaceAll(" ", "").replaceAll("IDR", "").replaceAll("Rp", "");
                double price;
                try {
                    price = Double.parseDouble(cleanString);
                } catch (NumberFormatException e) {
                    editPrice.addTextChangedListener(this);
                    return;
                }
                String formatted = getContext().getResources().getString(R.string.price_format, price);
                editPrice.setText(formatted);
                editPrice.addTextChangedListener(this);
                editPrice.setSelection(formatted.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        builder.setView(promptsView);
        builder.setPositiveButton(getContext().getString(R.string.ok), (dialog, which) -> {
            if (editPrice.getText().toString().isEmpty()) {
                return;
            }
            if (itemUpdatedListener == null) {
                dialog.dismiss();
                return;
            }

            String cleanString = editPrice.getText().toString().trim().replaceAll("[$,.]", "").replaceAll(" ", "").replaceAll("IDR", "").replaceAll("Rp", "");
            double editedPrice;
            try {
                editedPrice = Double.parseDouble(cleanString);
            } catch (NumberFormatException e) {
                return;
            }
            itemUpdatedListener.sellingPriceUpdate(barcode, editedPrice);
            dialog.dismiss();
        });
        builder.setNegativeButton(getContext().getString(R.string.cancel), ((dialog, which) -> {
            dialog.dismiss();
        }));


        AlertDialog dialog = builder.create();
        dialog.show();

        showSoftKeyboard(editPrice);
    }

}
