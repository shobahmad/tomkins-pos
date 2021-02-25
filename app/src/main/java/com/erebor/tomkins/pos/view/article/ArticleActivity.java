package com.erebor.tomkins.pos.view.article;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseActivity;
import com.erebor.tomkins.pos.data.ui.ArticleUiModel;
import com.erebor.tomkins.pos.databinding.ActivityArticleBinding;
import com.erebor.tomkins.pos.di.AppComponent;
import com.erebor.tomkins.pos.helper.ResourceHelper;
import com.erebor.tomkins.pos.viewmodel.article.ArticleViewModel;
import com.erebor.tomkins.pos.viewmodel.article.ArticleViewState;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;

import javax.inject.Inject;

public class ArticleActivity extends BaseActivity<ActivityArticleBinding> {
    @Inject
    ResourceHelper resourceHelper;
    @Inject
    ViewModelProvider.Factory factory;
    ArticleViewModel articleViewModel;
    private ArticleAdapter articleAdapter;
    @Override
    public void inject(AppComponent appComponent) {
        appComponent.doInjection(this);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_article;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbar(binding.toolbar.toolbar);
        binding.toolbar.setTitle(resourceHelper.getResourceString(R.string.search_article));
        articleViewModel = ViewModelProviders.of(this, factory).get(ArticleViewModel.class);

        setupAdapter();
        setupSearchView();
        observeDataChanged();
        setupConfirmButton();
        setupTransactionType();

        String barcode = getIntent().getStringExtra("barcode");
        if (barcode == null) {
            return;
        }
        binding.search.setQuery(barcode, true);
    }

    private void setupInputPrice() {
        binding.textPrice.setEndIconOnClickListener(v -> {
            System.out.println(binding.getArticle());
            inputPriceDialog();
        });
        binding.textPrice.setOnClickListener(v -> {
            System.out.println(binding.getArticle());
            inputPriceDialog();
        });
        binding.editPrice.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                return;
            }

            inputPriceDialog();
        });
        binding.textDiscount.setEndIconOnClickListener(v -> inputDiscountDialog());
        binding.editDiscount.setOnClickListener(v -> inputDiscountDialog());
        binding.editDiscount.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                return;
            }

            inputDiscountDialog();
        });
    }

    private void setupTransactionType() {
        binding.imageTransaction.setImageResource(R.drawable.ic_transaction_sale);
        binding.switchTransType.setText(getResources().getText(R.string.transaction_sale));
        binding.switchTransType.setChecked(true);
        binding.switchTransType.setOnCheckedChangeListener((buttonView, isChecked) -> {
            setTransactionType(isChecked);
        });
    }

    private void setTransactionType(boolean isSale) {
        binding.imageTransaction.setImageResource(isSale ? R.drawable.ic_transaction_sale : R.drawable.ic_transaction_return);
        binding.switchTransType.setText(getResources().getText(isSale ? R.string.transaction_sale : R.string.transaction_return));
        binding.switchTransType.setTextColor(getResources().getColor(
                isSale ? R.color.colorPrimary : R.color.colorPrimaryDark));
    }

    private void setupAdapter() {
        articleAdapter = new ArticleAdapter(this);
        articleAdapter.addListener(item -> {
            ArticleUiModel articleUiModel = (ArticleUiModel) item;
            articleViewModel.selectArticle(articleUiModel.getBarcode());

            binding.setArticle(articleUiModel);
            setupInputPrice();
        });
        binding.recyclerTransaction.setAdapter(articleAdapter);
        binding.recyclerTransaction.setLayoutManager(new LinearLayoutManager(this));
    }

    private void observeDataChanged() {
        articleViewModel.getViewState().observe(this, articleViewState -> {
            if (articleViewState.getCurrentState().equals(ArticleViewState.LOADING_STATE.getCurrentState())) {
                binding.setLoading(getString(R.string.searching_articles));
                articleAdapter.clearList();
                setupConfirmButton();
                return;
            }
            if (articleViewState.getCurrentState().equals(ArticleViewState.FOUND_STATE.getCurrentState())) {
                binding.setLoading(null);
                binding.setEmpty(false);
                articleAdapter.clearList();
                articleAdapter.setList(articleViewState.getData());
                if (articleViewState.getData().size() == 1) {
                    binding.setArticle(articleViewState.getData().get(0));
                }
                setupConfirmButton();
                return;
            }
            if (articleViewState.getCurrentState().equals(ArticleViewState.NOT_FOUND_STATE.getCurrentState())) {
                binding.setLoading(null);
                binding.setEmpty(true);
                articleAdapter.clearList();
                setupConfirmButton();
                return;
            }
            if (articleViewState.getCurrentState().equals(ArticleViewState.ERROR_STATE.getCurrentState())) {
                binding.setLoading(articleViewState.getError().getMessage());
                binding.setEmpty(false);
                articleAdapter.clearList();
                setupConfirmButton();
                return;
            }


        });
    }

    private void setupSearchView() {
        binding.search.requestFocus();
        EditText searchEditText = binding.search.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setTextColor(Color.BLACK);
        searchEditText.setHintTextColor(Color.BLACK);

        binding.search.setMaxWidth(Integer.MAX_VALUE);
        binding.search.setIconifiedByDefault(false);
        binding.search.setIconified(false);
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() < 3) {
                    return false;
                }
                articleViewModel.searchArticle(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setupConfirmButton() {
        if (articleAdapter.getList() == null) {
            binding.buttonConfirm.setEnabled(false);
            return;
        }

        for (ArticleUiModel articleUiModel : articleAdapter.getList()) {
            if (articleUiModel.isSelected()) {
                binding.buttonConfirm.setEnabled(true);
                binding.buttonConfirm.setOnClickListener(v -> {
                    Intent intent = new Intent();
                    intent.putExtra("barcode", articleUiModel.getBarcode());
                    intent.putExtra("is_sale", binding.switchTransType.isChecked());
                    intent.putExtra("note", binding.editNote.getText().toString());
                    intent.putExtra("discount", binding.editDiscount.getText().toString());
                    intent.putExtra("price", binding.editSubtotal.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                });
                return;
            }
        }

        binding.buttonConfirm.setEnabled(false);
    }


    private void inputDiscountDialog() {
        if (binding.getArticle() == null) {
            return;
        }
        String artikelName = binding.getArticle().getArtName();

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog);
        builder.setTitle(R.string.discount);
        builder.setMessage(getResources().getString(R.string.transaction_discount_input, artikelName));
        builder.setCancelable(true);

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_input_discount, null);

        TextInputEditText editDiscount = promptsView.findViewById(R.id.editDiscount);
        Slider slider = promptsView.findViewById(R.id.sliderDiscount);

        editDiscount.setText(getResources().getString(R.string.discount_format, 0f));
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
                    editDiscount.setText(getResources().getString(R.string.discount_format, 0d));
                    slider.setValue(0f);
                    return;
                }

                if (value > 100) {
                    editDiscount.setText(getResources().getString(R.string.discount_format, 100d));
                    slider.setValue(100f);
                    return;
                }

                slider.setValue(((Number) value).floatValue());
            }
        });
        slider.setValue(0f);
        slider.addOnChangeListener((slider1, value, fromUser) -> {
            if (!fromUser) {
                return;
            }
            editDiscount.setText(getResources().getString(R.string.discount_format, ((Number) value).doubleValue()));
        });

        builder.setView(promptsView);
        builder.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
            dialog.dismiss();

            if (editDiscount.getText().toString().isEmpty()) {
                return;
            }

            String cleanString = editDiscount.getText().toString().trim().replaceAll("[$,.]", "").replaceAll(" ", "").replaceAll("IDR", "").replaceAll("Rp", "");
            double discountPercentage = 0;
            try {
                discountPercentage = Double.parseDouble(cleanString.replace("%", ""));
            } catch (NumberFormatException e) {
            }

            binding.setArticle(new ArticleUiModel(binding.getArticle(), binding.getArticle().getHargaNormal(), discountPercentage));
            dialog.dismiss();

        });
        builder.setNegativeButton(getString(R.string.cancel), ((dialog, which) -> {
            dialog.dismiss();
        }));


        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void inputPriceDialog() {
        if (binding.getArticle() == null) {
            return;
        }
        String artikelName = binding.getArticle().getArtName();
        final Double price = binding.getArticle().getHargaNormal();

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.Theme_AppCompat_Dialog);
        builder.setTitle(R.string.transaction_selling_price);
        builder.setMessage(getResources().getString(R.string.transaction_price_input, artikelName));
        builder.setCancelable(true);

        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.dialog_input_price, null);

        TextInputEditText editPrice = promptsView.findViewById(R.id.editPrice);

        editPrice.setText(getResources().getString(R.string.price_format, price));
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
                String formatted = getResources().getString(R.string.price_format, price);
                editPrice.setText(formatted);
                editPrice.addTextChangedListener(this);
                editPrice.setSelection(formatted.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        builder.setView(promptsView);
        builder.setPositiveButton(getString(R.string.ok), (dialog, which) -> {
            if (editPrice.getText().toString().isEmpty()) {
                return;
            }

            String cleanString = editPrice.getText().toString().trim().replaceAll("[$,.]", "").replaceAll(" ", "").replaceAll("IDR", "").replaceAll("Rp", "");
            double editedPrice;
            try {
                editedPrice = Double.parseDouble(cleanString);
            } catch (NumberFormatException e) {
                return;
            }
            binding.setArticle(new ArticleUiModel(binding.getArticle(), editedPrice, 0));
            dialog.dismiss();
        });
        builder.setNegativeButton(getString(R.string.cancel), ((dialog, which) -> {
            dialog.dismiss();
        }));


        AlertDialog dialog = builder.create();
        dialog.show();

        showSoftKeyboard(editPrice);
    }

    public void showSoftKeyboard(View view) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }
}
