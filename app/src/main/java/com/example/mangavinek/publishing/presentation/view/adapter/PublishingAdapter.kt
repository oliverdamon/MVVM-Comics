package com.example.mangavinek.publishing.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mangavinek.R
import com.example.mangavinek.publishing.model.domain.mock.PublishingObject
import kotlinx.android.synthetic.main.row_data.view.*

class PublishingAdapter(private var listItem: ArrayList<PublishingObject>,
                        private val onItemClickListener: ((publishingObject: PublishingObject) -> Unit)):
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

    class ItemViewHolder(private val view: View, private val onItemClickListener: ((publishingObject: PublishingObject) -> Unit)):
        RecyclerView.ViewHolder(view) {

        private val title = view.text_title
        private val image = view.image_cover

        fun bindView(publishingObject: PublishingObject) = with(view){
            title.text = publishingObject.name

            this.setOnClickListener {
                onItemClickListener.invoke(publishingObject)
            }

        }
    }
}