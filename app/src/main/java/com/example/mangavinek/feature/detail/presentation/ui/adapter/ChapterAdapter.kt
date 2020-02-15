package com.example.mangavinek.feature.detail.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.mangavinek.R
import com.example.mangavinek.core.util.gifDecoder
import com.example.mangavinek.data.model.detail.domain.DetailChapterDomain
import kotlinx.android.synthetic.main.row_data.view.*

class ChapterAdapter(private var listDetailChapterDomain: ArrayList<DetailChapterDomain>, private val onItemClickListener: ((detailChapterDomain: DetailChapterDomain) -> Unit)) :
    RecyclerView.Adapter<ChapterAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.row_chapter, p0, false)
        return ItemViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = listDetailChapterDomain.size

    override fun onBindViewHolder(holder: ItemViewHolder, p1: Int) {

        val dataItem = listDetailChapterDomain[p1]
        holder.bindView(dataItem)
    }

    class ItemViewHolder(private val view: View, private val onItemClickListener: ((detailChapterDomain: DetailChapterDomain) -> Unit)) : RecyclerView.ViewHolder(view) {
        private val imageCover = view.image_cover

        fun bindView(detailChapterDomain: DetailChapterDomain) = with(view){

            imageCover.alpha = 0.3f
            imageCover.animate().setDuration(400).setInterpolator(AccelerateDecelerateInterpolator()).alpha(1f)

            imageCover.load(detailChapterDomain.imageCover,
                gifDecoder(context)
            ) { placeholder(R.drawable.ic_image_24dp) }

            this.setOnClickListener {
                onItemClickListener.invoke(detailChapterDomain)
            }
        }
    }
}