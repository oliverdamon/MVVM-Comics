package com.example.mangavinek.presentation.detail.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_data.view.*
import com.example.mangavinek.R
import com.example.mangavinek.model.detail.entity.StatusChapter
import kotlinx.android.synthetic.main.row_chapter_status.view.*

class StatusChapterAdapter(private var listItem: List<StatusChapter>, private var context: Context) :
    RecyclerView.Adapter<StatusChapterAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_chapter_status, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ItemViewHolder, p1: Int) {

        val dataItem = listItem[p1]

        when (dataItem.status){
            "available" -> holder.title.setBackgroundColor(ContextCompat.getColor(context, R.color.available_color))
            "unavailable" -> holder.title.setBackgroundColor(ContextCompat.getColor(context, R.color.unavailable_color))
            "translated" -> holder.title.setBackgroundColor(ContextCompat.getColor(context, R.color.translated_color))
        }

        holder.title.text = dataItem.number
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.text_number_chapter
    }
}