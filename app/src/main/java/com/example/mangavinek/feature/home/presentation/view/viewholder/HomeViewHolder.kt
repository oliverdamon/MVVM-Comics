package com.example.mangavinek.feature.home.presentation.view.viewholder

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mangavinek.R
import com.example.mangavinek.core.helper.AdapterPagination
import com.example.mangavinek.feature.home.model.domain.NewChapterDomain
import kotlinx.android.synthetic.main.row_data.view.*

class HomeViewHolder(private val view: View, private val onItemClickListener: (newChapterDomain: NewChapterDomain) -> Unit) :
    AdapterPagination.CustomerViewHolder(view) {

    private val title = view.text_title
    private val imageCover = view.image_cover

    override fun bindView(item: Any) = with(view) {
        val newChapterDomain = item as NewChapterDomain

        title.text = newChapterDomain.title

        imageCover.alpha = 0.3f
        imageCover.animate().setDuration(400).setInterpolator(AccelerateDecelerateInterpolator()).alpha(1f)

        Glide.with(this)
            .load(newChapterDomain.image)
            .placeholder(R.drawable.ic_image_24dp)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageCover)

        this.setOnClickListener {
            onItemClickListener.invoke(newChapterDomain)
        }
    }
}
