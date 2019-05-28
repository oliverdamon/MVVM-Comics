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



class ItemAdapter(private var listItem: ArrayList<Model>, private var context: Context) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_data, p0, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = listItem.size

    override fun onBindViewHolder(p0: ItemViewHolder, p1: Int) {

        val dataItem = listItem[p1]
        //val attributes = dataItem.attributes
        //val posterImage = attributes.posterImage

        p0.title.text = dataItem.title

        Glide.with(context)
            .load(dataItem.image)
            .into(p0.image)
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