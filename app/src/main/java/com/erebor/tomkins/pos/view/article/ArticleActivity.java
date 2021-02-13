package com.erebor.tomkins.pos.view.article;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
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
    }

    private void setupAdapter() {
        articleAdapter = new ArticleAdapter(this);
        articleAdapter.addListener(item -> {
            ArticleUiModel articleUiModel = (ArticleUiModel) item;
            articleViewModel.selectArticle(articleUiModel.getBarcode());
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
                    intent.putExtra("data", articleUiModel.getBarcode());
                    setResult(RESULT_OK, intent);
                    finish();
                });
                return;
            }
        }

        binding.buttonConfirm.setEnabled(false);
    }
}
