package com.example.mangavinek.feature.catalog.presentation.ui.viewholder

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import coil.api.load
import com.example.mangavinek.R
import com.example.mangavinek.core.constant.BASE_URL
import com.example.mangavinek.core.helper.AdapterPagination
import com.example.mangavinek.data.model.catalog.domain.CatalogDomain
import kotlinx.android.synthetic.main.row_data.view.*

class CatalogViewHolder(private val view: View, private val onItemClickListener: ((catalogDomain: CatalogDomain) -> Unit)):
    AdapterPagination.CustomViewHolder(view) {

    private val title = view.text_title
    private val imageCover = view.image_cover

    override fun bindView(item: Any) = with(view){
        val catalogDomain = item as CatalogDomain
        title.text = catalogDomain.title

        imageCover.alpha = 0.3f
        imageCover.animate().setDuration(400).setInterpolator(AccelerateDecelerateInterpolator()).alpha(1f)

        imageCover.load(BASE_URL.plus(catalogDomain.image)) { placeholder(R.drawable.ic_image_24dp) }

        this.setOnClickListener {
            onItemClickListener.invoke(catalogDomain)
        }
    }
}