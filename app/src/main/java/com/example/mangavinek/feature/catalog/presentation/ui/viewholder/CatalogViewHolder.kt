package com.example.mangavinek.feature.catalog.presentation.ui.viewholder

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mangavinek.R
import com.example.mangavinek.core.helper.AdapterPagination
import com.example.mangavinek.core.constant.BASE_URL
import com.example.mangavinek.data.model.catalog.domain.CatalogDomain
import kotlinx.android.synthetic.main.row_data.view.*

class CatalogViewHolder(private val view: View, private val onItemClickListener: ((catalogDomain: CatalogDomain) -> Unit)):
    AdapterPagination.CustomerViewHolder(view) {

    private val title = view.text_title
    private val imageCover = view.image_cover

    override fun bindView(item: Any) = with(view){
        val catalogDomain = item as CatalogDomain
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