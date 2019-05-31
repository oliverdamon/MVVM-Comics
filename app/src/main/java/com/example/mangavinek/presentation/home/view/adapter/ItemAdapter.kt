package com.example.mangavinek.presentation.home.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_data.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mangavinek.model.home.entity.Model
import com.example.mangavinek.R


class ItemAdapter(private var listItem: ArrayList<Model>, private var context: Context) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_data, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ItemViewHolder, p1: Int) {

        val dataItem = listItem[p1]

        holder.title.text = dataItem.title

        holder.image.alpha = 0.3f
        holder.image.animate().setDuration(400).setInterpolator(AccelerateDecelerateInterpolator()).alpha(1f)
        Glide.with(context)
            .load(dataItem.image)
            .apply(
                RequestOptions()
                    .fitCenter()
                    .format(DecodeFormat.PREFER_ARGB_8888)
            )
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image)

    }

    fun clear(datas: ArrayList<Model>) {
        listItem.clear()
        listItem.addAll(datas)
        notifyDataSetChanged()
    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.text_title
        val image = view.image_cover
    }
}