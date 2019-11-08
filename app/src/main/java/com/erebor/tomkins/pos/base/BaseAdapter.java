package com.erebor.tomkins.pos.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.erebor.tomkins.pos.view.callback.IItemClick;

import java.util.List;

public abstract class BaseAdapter<B extends ViewDataBinding, T> extends RecyclerView.Adapter<BaseAdapter.ViewHolder> {

    private static final String TAG = "BaseAdapter";
    public B binding;
    private List<T> list;
    private LayoutInflater layoutInflater;
    private IItemClick<T> listener;
    private Context context;

    public BaseAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public abstract void setDataBinding(ViewHolder viewHolder, T data);

    public abstract B inflate(LayoutInflater layoutInflater, ViewGroup parent);

    public abstract boolean areItemsTheSame(T oldItem, T newItem);

    public boolean areContentsTheSame(T oldItem, T newItem) {
        return false;
    }

    public abstract T getItem(ViewHolder viewHolder);

    public void addListener(IItemClick itemClick) {
        this.listener = itemClick;
    }

    public void removeListener() {
        listener = null;
    }

    public List<T> getList() {
        return list;
    }

    public void clearList() {
        if (this.list == null) {
            return;
        }
        this.list.clear();
        notifyDataSetChanged();
    }

    public void addList(final List<T> newList) {
        if (this.list == null) {
            this.list = newList;
            notifyItemRangeInserted(0, newList.size());
            return;
        }

        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override
            public int getOldListSize() {
                return BaseAdapter.this.list.size();
            }

            @Override
            public int getNewListSize() {
                return newList.size();
            }

            @Override
            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return BaseAdapter.this.areItemsTheSame(BaseAdapter.this.list.get(oldItemPosition), newList.get(newItemPosition));
            }

            @Override
            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return BaseAdapter.this.areContentsTheSame(BaseAdapter.this.list.get(oldItemPosition), newList.get(newItemPosition));
            }
        });
        this.list = newList;
        result.dispatchUpdatesTo(this);

    }

    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(int position, T item) {
        list.add(position, item);
        notifyItemInserted(position);
    }

    public T getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = inflate(layoutInflater, parent);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setDataBinding(holder, getItem(position));

        binding.getRoot().setOnClickListener(v -> {
            if (listener != null) listener.onItemClick(getItem(holder));
        });
    }

    @Override
    public int getItemCount() {
        return this.list == null ? 0 : this.list.size();
    }

    public Context getContext() {
        return context;
    }

    protected final static class ViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
        private B binding;

        ViewHolder(B binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public B getBinding() {
            return binding;
        }
    }
}
