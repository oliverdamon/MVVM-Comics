package com.example.mangavinek

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mangavinek.entity.Data
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_data.view.*
import android.text.method.TextKeyListener.clear
import android.view.animation.AccelerateDecelerateInterpolator
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


class ItemAdapter(private var listItem: ArrayList<Model>, private var context: Context) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_data, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(holder: ItemViewHolder, p1: Int) {

        val dataItem = listItem[p1]
        //val attributes = dataItem.attributes
        //val posterImage = attributes.posterImage

        holder.title.text = dataItem.title

        holder.image.alpha =  0.3f
        holder.image.animate().setDuration(400).setInterpolator(AccelerateDecelerateInterpolator()).alpha(1f)
        Glide.with(context)
            .load(dataItem.image)
            .apply(
                RequestOptions()
                    .fitCenter()
                    .format(DecodeFormat.PREFER_ARGB_8888))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.image)

    }

    fun clear(datas: ArrayList<Model>) {    //your bean
        this.listItem.clear()          //mStrings is your bean ArrayList
        this.listItem.addAll(datas)
        notifyDataSetChanged()
    }

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val title = view.text_title
        val image = view.image_cover
    }
}