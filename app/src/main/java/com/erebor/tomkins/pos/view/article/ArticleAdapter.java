package com.erebor.tomkins.pos.view.article;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.erebor.tomkins.pos.R;
import com.erebor.tomkins.pos.base.BaseAdapter;
import com.erebor.tomkins.pos.data.ui.ArticleUiModel;
import com.erebor.tomkins.pos.databinding.ItemArticleBinding;
import com.erebor.tomkins.pos.view.callback.EditArticleListener;
import com.erebor.tomkins.pos.view.callback.EditStockListener;

public class ArticleAdapter extends BaseAdapter<ItemArticleBinding, ArticleUiModel> {

    private final EditArticleListener editArticleListener;
    public ArticleAdapter(Context context, EditArticleListener editArticleListener) {
        super(context);
        this.editArticleListener = editArticleListener;
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


        binding.switchGrade.setOnCheckedChangeListener(null);
        binding.switchGrade.setChecked(binding.getArticle().getGrade().equals("A"));
        binding.switchGrade.setText(getContext().getString(R.string.grade) + " " +  binding.getArticle().getGrade());
        binding.switchGrade.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editArticleListener.onEditGrade(binding.getArticle(), isChecked ? "A" : "B");
        });
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
