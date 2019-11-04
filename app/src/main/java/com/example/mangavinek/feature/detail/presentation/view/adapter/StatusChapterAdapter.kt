package com.example.mangavinek.feature.detail.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mangavinek.R
import com.example.mangavinek.feature.detail.model.domain.StatusChapterDomain
import kotlinx.android.synthetic.main.row_chapter_status.view.*

class StatusChapterAdapter(private var listStatusChapterDomain: List<StatusChapterDomain>) :
    RecyclerView.Adapter<StatusChapterAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.row_chapter_status, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = listStatusChapterDomain.size

    override fun onBindViewHolder(holder: ItemViewHolder, p1: Int) {

        val dataItem = listStatusChapterDomain[p1]
        holder.bindView(dataItem)
    }

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val title = view.text_number_chapter

        fun bindView(statusChapterDomain: StatusChapterDomain) = with(view) {
            when (statusChapterDomain.status) {
                StatusChapterDomain.AVALAIBLE -> title.setBackgroundColor(ContextCompat.getColor(context, R.color.available_color))
                StatusChapterDomain.TRANSLATED -> title.setBackgroundColor(ContextCompat.getColor(context, R.color.translated_color))
                StatusChapterDomain.UNAVAILABLE -> title.setBackgroundColor(ContextCompat.getColor(context, R.color.unavailable_color))
            }

            title.text = statusChapterDomain.number
        }
    }
}