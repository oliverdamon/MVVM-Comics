package com.example.mangavinek.core.helper

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mangavinek.R
import com.example.mangavinek.core.customview.ItemReload
import kotlinx.android.synthetic.main.layout_item_bottom.view.*

class AdapterPagination(
    private val getLayout: Int,
    private val viewHolder: (View) -> RecyclerView.ViewHolder,
    private val onRetryClickListener: () -> Unit = {}): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val ITEM_LIST = 0
        const val ITEM_BOTTOM = 1
    }

    private var isLoadingAdded = false
    private var retryPageLoad = false
    private var listItem = ArrayList<Any>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return if (p1 == ITEM_LIST) {
            viewHolder(LayoutInflater.from(p0.context).inflate(getLayout, p0, false))
        } else {
            ItemBottomViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.layout_item_bottom, p0, false), onRetryClickListener)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {
        when (holder) {
            is CustomerViewHolder -> {
                val dataItem = listItem[p1]
                holder.bindView(dataItem)
            }
            is ItemBottomViewHolder -> {
                holder.bindView(retryPageLoad)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (isLoadingAdded) {
            listItem.size + 1
        } else {
            listItem.size
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position < listItem.size) ITEM_LIST else ITEM_BOTTOM
    }

    fun addList(dataItem: List<Any>) {
        this.listItem.addAll(dataItem)
        notifyItemChanged(this.listItem.size - dataItem.size, this.listItem.size)

        addItemBottom()
    }

    fun clearList() {
        isLoadingAdded = false
        this.listItem.clear()
        notifyDataSetChanged()
    }

    fun addItemBottom() {
        isLoadingAdded = true
    }

    fun removeItemBottom() {
        isLoadingAdded = false
        notifyItemRangeRemoved(this.listItem.size, 1)
    }

    fun showErrorRetry(showError: Boolean) {
        retryPageLoad = showError
        notifyItemChanged(this.listItem.size, 1)
    }

    abstract class CustomerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bindView(item: Any)
    }

    class ItemBottomViewHolder(private val view: View, private val onRetryClickListener: () -> Unit = {}) :
        RecyclerView.ViewHolder(view) {

        private val itemBottom: ItemReload = view.item_bottom

        fun bindView(retryPageLoad: Boolean) = with(view) {
            if (retryPageLoad) {
                itemBottom.showErrorRetry()
            } else {
                itemBottom.showLoading()
            }

            itemBottom.buttonRetry.setOnClickListener {
                onRetryClickListener.invoke()
            }
        }
    }
}