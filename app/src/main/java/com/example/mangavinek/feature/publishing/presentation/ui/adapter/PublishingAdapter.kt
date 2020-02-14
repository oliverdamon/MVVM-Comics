package com.example.mangavinek.feature.publishing.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mangavinek.R
import com.example.mangavinek.data.model.publishing.domain.PublishingDomain
import kotlinx.android.synthetic.main.row_data.view.*

class PublishingAdapter(
    private var listItem: ArrayList<PublishingDomain>,
    private val onItemClickListener: ((publishingDomain: PublishingDomain) -> Unit)
) :
    RecyclerView.Adapter<PublishingAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.row_data_publishing, p0, false)
        return ItemViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ItemViewHolder, p1: Int) {

        val dataItem = listItem[p1]
        holder.bindView(dataItem)
    }

    class ItemViewHolder(
        private val view: View,
        private val onItemClickListener: ((publishingDomain: PublishingDomain) -> Unit)
    ) :
        RecyclerView.ViewHolder(view) {

        private val title = view.text_title
        private val image = view.image_cover

        fun bindView(publishingDomain: PublishingDomain) = with(view) {
            title.text = publishingDomain.name
            image.setImageDrawable(ContextCompat.getDrawable(context, publishingDomain.image))

            this.setOnClickListener {
                onItemClickListener.invoke(publishingDomain)
            }

        }
    }
}