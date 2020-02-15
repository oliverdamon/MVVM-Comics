package com.example.mangavinek.feature.home.presentation.ui.viewholder

import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import coil.api.load
import com.example.mangavinek.R
import com.example.mangavinek.core.helper.AdapterPagination
import com.example.mangavinek.core.util.gifDecoder
import com.example.mangavinek.data.model.home.domain.NewChapterDomain
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

        imageCover.load(newChapterDomain.image,
            gifDecoder(context)
        ) { placeholder(R.drawable.ic_image_24dp) }

        this.setOnClickListener {
            onItemClickListener.invoke(newChapterDomain)
        }
    }
}
