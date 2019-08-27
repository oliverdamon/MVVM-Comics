package com.example.mangavinek.favorite.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mangavinek.R
import com.example.mangavinek.data.model.favorite.entity.Favorite
import kotlinx.android.synthetic.main.row_data.view.*

class FavoriteAdapter(private var listItem: ArrayList<Favorite>,
                      private val onItemClickListener: ((favorite: Favorite) -> Unit)):
    RecyclerView.Adapter<FavoriteAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.row_data, p0, false)
        return ItemViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ItemViewHolder, p1: Int) {

        val dataItem = listItem[p1]
        holder.bindView(dataItem)
    }



    fun clear(datas: ArrayList<Favorite>) {
        listItem.clear()
        listItem.addAll(datas)
        notifyDataSetChanged()
    }

    class ItemViewHolder(private val view: View, private val onItemClickListener: ((favorite: Favorite) -> Unit)):
        RecyclerView.ViewHolder(view) {

        private val title = view.text_title
        private val image = view.image_cover

        fun bindView(favorite: Favorite) = with(view){
            title.text = favorite.title

            image.alpha = 0.3f
            image.animate().setDuration(400).setInterpolator(AccelerateDecelerateInterpolator()).alpha(1f)
            Glide.with(this)
                .load(favorite.image)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_image_24dp)
                )
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image)

            this.setOnClickListener {
                onItemClickListener.invoke(favorite)
            }

        }
    }
}