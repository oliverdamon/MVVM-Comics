package com.example.mangavinek.home.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mangavinek.R
import com.example.mangavinek.data.model.home.entity.HomeResponse
import kotlinx.android.synthetic.main.row_data.view.*

class HomeAdapter(private var listItem: ArrayList<HomeResponse>,
                  private val onItemClickListener: ((homeResponse: HomeResponse) -> Unit)):
    RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.row_data, p0, false)
        return ItemViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ItemViewHolder, p1: Int) {

        val dataItem = listItem[p1]
        holder.bindView(dataItem)
    }

    fun clear(datas: ArrayList<HomeResponse>) {
        listItem.clear()
        listItem.addAll(datas)
        notifyDataSetChanged()
    }

    class ItemViewHolder(private val view: View, private val onItemClickListener: ((homeResponse: HomeResponse) -> Unit)):
        RecyclerView.ViewHolder(view) {

        private val title = view.text_title
        private val image = view.image_cover

        fun bindView(homeResponse: HomeResponse) = with(view){
            title.text = homeResponse.title

            image.alpha = 0.3f
            image.animate().setDuration(400).setInterpolator(AccelerateDecelerateInterpolator()).alpha(1f)
            Glide.with(this)
                .load(homeResponse.image)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_image_24dp)
                )
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image)

            this.setOnClickListener {
                onItemClickListener.invoke(homeResponse)
            }

        }
    }
}