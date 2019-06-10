package com.example.mangavinek.presentation.detail.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_data.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mangavinek.R
import com.example.mangavinek.model.detail.entity.DetailResponse

class ChapterAdapter(private var listItem: ArrayList<DetailResponse>, private var context: Context) :
    RecyclerView.Adapter<ChapterAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_chapter, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ItemViewHolder, p1: Int) {

        val dataItem = listItem[p1]

        holder.image.alpha = 0.3f
        holder.image.animate().setDuration(400).setInterpolator(AccelerateDecelerateInterpolator()).alpha(1f)
        Glide.with(context)
            .load(dataItem.imageChapter)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.ic_image_24dp)
            )
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image)

        holder.image.setOnClickListener {
        }
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image = view.image_cover
    }
}