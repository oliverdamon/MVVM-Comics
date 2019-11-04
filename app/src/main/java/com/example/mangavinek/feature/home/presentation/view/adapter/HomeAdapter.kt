package com.example.mangavinek.feature.home.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.mangavinek.R
import com.example.mangavinek.feature.home.model.domain.NewChapterDomain
import kotlinx.android.synthetic.main.row_data.view.*

class HomeAdapter(private var listNewChapterDomain: ArrayList<NewChapterDomain>,
                  private val onItemClickListener: ((newChapterDomain: NewChapterDomain) -> Unit)):
    RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.row_data, p0, false)
        return ItemViewHolder(view, onItemClickListener)
    }

    override fun getItemCount(): Int = listNewChapterDomain.size

    override fun onBindViewHolder(holder: ItemViewHolder, p1: Int) {

        val dataItem = listNewChapterDomain[p1]
        holder.bindView(dataItem)
    }

    fun clear(listNewChapterDomain: ArrayList<NewChapterDomain>) {
        this.listNewChapterDomain.clear()
        this.listNewChapterDomain.addAll(listNewChapterDomain)
        notifyDataSetChanged()
    }

    class ItemViewHolder(private val view: View, private val onItemClickListener: ((newChapterDomain: NewChapterDomain) -> Unit)):
        RecyclerView.ViewHolder(view) {

        private val title = view.text_title
        private val imageCover = view.image_cover

        fun bindView(newChapterDomain: NewChapterDomain) = with(view){
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
}