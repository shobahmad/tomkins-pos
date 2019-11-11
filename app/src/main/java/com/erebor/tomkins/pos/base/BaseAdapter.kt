package com.erebor.tomkins.pos.base

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView


import com.erebor.tomkins.pos.view.callback.IItemClick

abstract class BaseAdapter<B : ViewDataBinding, T>(context: Context) : RecyclerView.Adapter<BaseAdapter.ViewHolder<B>>() {
    lateinit var binding: B
    private var list: MutableList<T>? = null
    private val layoutInflater: LayoutInflater
    private var listener: IItemClick<T>? = null

    init {
        layoutInflater = LayoutInflater.from(context)
    }

    protected abstract fun setDataBinding(viewHolder: ViewHolder<B>, data: T)
    protected abstract fun getItem(viewHolder: ViewHolder<B>): T

    abstract fun inflate(layoutInflater: LayoutInflater, parent: ViewGroup): B

    abstract fun areItemsTheSame(oldItem: T, newItem: T): Boolean

    fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return false
    }

    fun addListener(itemClick: IItemClick<T>) {
        this.listener = itemClick
    }

    fun removeListener() {
        listener = null
    }

    fun getList(): List<T>? {
        return list
    }

    fun clearList() {
        if (this.list == null) {
            return
        }
        this.list!!.clear()
        notifyDataSetChanged()
    }

    fun addList(newList: MutableList<T>) {
        if (this.list == null) {
            this.list = newList
            notifyItemRangeInserted(0, newList.size)
            return
        }

        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return this@BaseAdapter.list!!.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return this@BaseAdapter.areItemsTheSame(this@BaseAdapter.list!![oldItemPosition], newList[newItemPosition])
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return this@BaseAdapter.areContentsTheSame(this@BaseAdapter.list!![oldItemPosition], newList[newItemPosition])
            }
        })
        this.list = newList
        result.dispatchUpdatesTo(this)

    }

    fun removeItem(position: Int) {
        list!!.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(position: Int, item: T) {
        list!!.add(position, item)
        notifyItemInserted(position)
    }

    fun getItemAt(position: Int): T {
        return list!![position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> {
        binding = inflate(layoutInflater, parent)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<B>, position: Int) {
        setDataBinding(holder, getItemAt(position))

        binding.root.setOnClickListener { v -> if (listener != null) listener!!.onItemClick(getItem(holder)) }
    }

    override fun getItemCount(): Int {
        return if (this.list == null) 0 else this.list!!.size
    }

    class ViewHolder<B : ViewDataBinding> internal constructor(val binding: B) : RecyclerView.ViewHolder(binding.getRoot())

    companion object {

        private val TAG = "BaseAdapter"
    }
}
