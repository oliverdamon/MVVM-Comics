package com.example.mangavinek.feature.favorite.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.mangavinek.R
import com.example.mangavinek.core.util.gifDecoder
import com.example.mangavinek.feature.model.favorite.entity.FavoriteDB
import kotlinx.android.synthetic.main.row_data.view.*

class FavoriteAdapter(private var listItem: ArrayList<FavoriteDB>,
                      private val onItemClickListener: ((favoriteDB: FavoriteDB) -> Unit)):
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

    fun atualiza(noticias: List<FavoriteDB>) {
        this.listItem.clear()
        this.listItem.addAll(noticias)
        notifyDataSetChanged()
    }

    class ItemViewHolder(private val view: View, private val onItemClickListener: ((favoriteDB: FavoriteDB) -> Unit)):
        RecyclerView.ViewHolder(view) {

        private val title = view.text_title
        private val imageCover = view.image_cover

        fun bindView(favoriteDB: FavoriteDB) = with(view){
            title.text = favoriteDB.title

            imageCover.alpha = 0.3f
            imageCover.animate().setDuration(400).setInterpolator(AccelerateDecelerateInterpolator()).alpha(1f)

            imageCover.load(favoriteDB.image, gifDecoder(context)) { placeholder(R.drawable.ic_image_24dp) }

            this.setOnClickListener {
                onItemClickListener.invoke(favoriteDB)
            }

        }
    }
}