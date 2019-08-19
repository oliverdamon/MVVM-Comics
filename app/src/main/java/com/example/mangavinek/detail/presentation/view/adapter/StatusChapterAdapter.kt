package com.example.mangavinek.detail.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mangavinek.R
import com.example.mangavinek.data.model.detail.entity.StatusChapter
import kotlinx.android.synthetic.main.row_chapter_status.view.*

class StatusChapterAdapter(private var listItem: List<StatusChapter>) :
    RecyclerView.Adapter<StatusChapterAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.row_chapter_status, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ItemViewHolder, p1: Int) {

        val dataItem = listItem[p1]
        holder.bindView(dataItem)
    }

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.text_number_chapter

        fun bindView(statusChapter: StatusChapter) = with(view) {
            when (statusChapter.status) {
                "available" -> title.setBackgroundColor(ContextCompat.getColor(context, R.color.available_color))
                "unavailable" -> title.setBackgroundColor(ContextCompat.getColor(context, R.color.unavailable_color))
                "translated" -> title.setBackgroundColor(ContextCompat.getColor(context, R.color.translated_color))
            }

            title.text = statusChapter.number
        }
    }
}