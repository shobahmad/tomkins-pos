package com.erebor.tomkins.pos.view.receive;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseAdapter;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaStockModel;
import com.erebor.tomkins.pos.databinding.ItemReceiveStockBinding;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class ReceiveStockAdapter extends BaseAdapter<ItemReceiveStockBinding, TrxTerimaStockModel> {

    private final EditStockListener editStockListener;
    public ReceiveStockAdapter(Context context, EditStockListener editStockListener) {
        super(context);
        this.editStockListener = editStockListener;
    }

    @Override
    public void setDataBinding(ViewHolder viewHolder, TrxTerimaStockModel data) {
        ItemReceiveStockBinding binding = (ItemReceiveStockBinding) viewHolder.getBinding();
        binding.setStock(data);
    }

    @Override
    public ItemReceiveStockBinding inflate(LayoutInflater layoutInflater, ViewGroup parent) {
        return ItemReceiveStockBinding.inflate(layoutInflater, parent, false);
    }

    @Override
    public boolean areItemsTheSame(TrxTerimaStockModel oldItem, TrxTerimaStockModel newItem) {
        return oldItem.getNoBarcode().equals(newItem.getNoBarcode());
    }

    @Override
    public TrxTerimaStockModel getItem(ViewHolder viewHolder) {
        ItemReceiveStockBinding binding = (ItemReceiveStockBinding) viewHolder.getBinding();
        return binding.getStock();
    }

    AlertDialog alertDialog;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        ItemReceiveStockBinding binding = (ItemReceiveStockBinding) holder.getBinding();
        binding.textQtyTerima.setEndIconOnClickListener(v -> showEditQtyDialog(binding));
        binding.textQtyTerima.setEndIconDrawable(binding.getStock().getQtyKirim() == binding.getStock().getQtyTerima() ? 0 : R.drawable.ic_stock_add);

        binding.layoutArt.setBackgroundResource(binding.getStock().getQtyKirim() == binding.getStock().getQtyTerima() ?
                R.drawable.background_rounded_selected : R.drawable.background_rounded_unselected);

        binding.switchGrade.setOnCheckedChangeListener(null);
        binding.switchGrade.setChecked(binding.getStock().getGrade().equals("A"));
        binding.switchGrade.setText(getContext().getString(R.string.grade) + " " +  binding.getStock().getGrade());
        binding.switchGrade.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editStockListener.onEditGrade(binding.getStock(), isChecked ? "A" : "B");
        });
    }

    private void showEditQtyDialog(ItemReceiveStockBinding binding) {
        if (binding.getStock().getQtyTerima() == binding.getStock().getQtyKirim()) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.snipped_stock_update, (ViewGroup) binding.getRoot(), false);
        MaterialTextView textArtName = viewInflated.findViewById(R.id.textArtName);
        textArtName.setText(binding.getStock().getNamaArt());

        int minStock = binding.getStock().getQtyTerima();
        int maxStock = binding.getStock().getQtyKirim();

        TextInputEditText editQtyKirim = viewInflated.findViewById(R.id.editQtyKirim);
        editQtyKirim.setText(String.valueOf(binding.getStock().getQtyKirim()));

        TextInputEditText editQtyTerima = viewInflated.findViewById(R.id.editQtyTerima);
        editQtyTerima.setText(String.valueOf(binding.getStock().getQtyTerima()));
        editQtyTerima.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int inputQty = getInputQty(s);
                if (inputQty < minStock) {
                    editQtyTerima.setError(getContext().getString(R.string.input_stock_invalid_minus));
                    return;
                }
                if (inputQty > maxStock) {
                    editQtyTerima.setError(getContext().getString(R.string.input_stock_invalid_max));
                    return;
                }

                editQtyTerima.setError(null);
            }
        });

        MaterialButton buttonSave = viewInflated.findViewById(R.id.button_save);
        buttonSave.setOnClickListener(v1 -> {
            if (editQtyTerima.getError() != null) {
                return;
            }
            if (editStockListener == null) {
                return;
            }

            editStockListener.onEditStock(binding.getStock(), getInputQty(editQtyTerima.getText()));
            alertDialog.dismiss();
        });

        builder.setView(viewInflated);
        alertDialog = builder.show();
    }

    private int getInputQty(Editable text) {
        if (text == null) {
            return 0;
        }
        try {
            return Integer.parseInt(text.toString());
        } catch (NumberFormatException e) {
            return 0;
        }
    }


}
