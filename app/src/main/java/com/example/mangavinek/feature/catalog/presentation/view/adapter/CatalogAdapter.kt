package com.example.mangavinek.feature.catalog.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mangavinek.R
import com.example.mangavinek.core.constant.BASE_URL
import com.example.mangavinek.feature.catalog.model.domain.CatalogDomain
import kotlinx.android.synthetic.main.row_data.view.*

class CatalogAdapter(var listCatalogDomain: ArrayList<CatalogDomain>,
                     private val onItemClickListener: ((catalogDomain: CatalogDomain) -> Unit)):
    RecyclerView.Adapter<CatalogAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.row_data, p0, false)
        return ItemViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = listCatalogDomain.size

    override fun onBindViewHolder(holder: ItemViewHolder, p1: Int) {

        val dataItem = listCatalogDomain[p1]
        holder.bindView(dataItem)
    }

    fun clear(listCatalogDomain: ArrayList<CatalogDomain>) {
        this.listCatalogDomain.clear()
        this.listCatalogDomain.addAll(listCatalogDomain)
        notifyDataSetChanged()
    }

    class ItemViewHolder(private val view: View, private val onItemClickListener: ((catalogDomain: CatalogDomain) -> Unit)):
        RecyclerView.ViewHolder(view) {

        val title = view.text_title
        val imageCover = view.image_cover

        fun bindView(catalogDomain: CatalogDomain) = with(view){
            title.text = catalogDomain.title

            imageCover.alpha = 0.3f
            imageCover.animate().setDuration(400).setInterpolator(AccelerateDecelerateInterpolator()).alpha(1f)
            Glide.with(this)
                .load(BASE_URL.plus(catalogDomain.image))
                .placeholder(R.drawable.ic_image_24dp)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageCover)

            this.setOnClickListener {
                onItemClickListener.invoke(catalogDomain)
            }

        }
    }
}