package com.erebor.tomkins.pos.base;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.TomkinApps;
import com.erebor.tomkins.pos.di.AppComponent;


public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity {

    protected B binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TomkinApps mainApplication = (TomkinApps) getApplication();
        inject(mainApplication.getAppComponent());
        binding = DataBindingUtil.setContentView(this, getLayout());
    }

    public abstract void inject(AppComponent appComponent);
    public abstract int getLayout();
    protected boolean isMenuSearchEnabled() {
        return false;
    }
    protected boolean onQueryTextSubmit(String query) {
        return false;
    }
    protected boolean onQueryTextChange(String newText) {
        return false;
    }

    protected void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayShowTitleEnabled(true);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!isMenuSearchEnabled()) {
            return super.onCreateOptionsMenu(menu);
        }

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) BaseActivity.this.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = null;
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView == null) {
            return super.onCreateOptionsMenu(menu);
        }
        searchView.setSearchableInfo(searchManager.getSearchableInfo(BaseActivity.this.getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return BaseActivity.this.onQueryTextSubmit(query);
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return BaseActivity.this.onQueryTextChange(newText);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
