package com.erebor.tomkins.pos.view.article;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseAdapter;
import com.erebor.tomkins.pos.data.ui.ArticleUiModel;
import com.erebor.tomkins.pos.databinding.ItemArticleBinding;

public class ArticleAdapter extends BaseAdapter<ItemArticleBinding, ArticleUiModel> {

    public ArticleAdapter(Context context) {
        super(context);
    }
    @Override
    public void setDataBinding(ViewHolder viewHolder, ArticleUiModel data) {
        ItemArticleBinding binding = (ItemArticleBinding) viewHolder.getBinding();
        binding.setArticle(data);
    }

    @Override
    public ItemArticleBinding inflate(LayoutInflater layoutInflater, ViewGroup parent) {
        return ItemArticleBinding.inflate(layoutInflater, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ItemArticleBinding binding = (ItemArticleBinding) holder.getBinding();
        binding.layoutOuter.setBackgroundResource(binding.getArticle().isSelected() ? R.drawable.background_rounded_selected : R.drawable.background_rounded_unselected);
    }

    @Override
    public boolean areItemsTheSame(ArticleUiModel oldItem, ArticleUiModel newItem) {
        return oldItem.getBarcode().equals(newItem.getBarcode());
    }

    @Override
    public ArticleUiModel getItem(ViewHolder viewHolder) {
        ItemArticleBinding binding = (ItemArticleBinding) viewHolder.getBinding();
        return binding.getArticle();
    }

}
