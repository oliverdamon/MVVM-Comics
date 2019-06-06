package com.example.mangavinek.presentation.detail.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.mangavinek.R
import com.example.mangavinek.api.ApiServiceSoup
import com.example.mangavinek.model.detail.entity.DetailResponse
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.select.Elements
import java.io.IOException

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val url: String = intent.getStringExtra("url")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response: Elements? = ApiServiceSoup.getDetail(url)
                withContext(Dispatchers.Main) {
                    response?.let {
                        val value = DetailResponse(it[0])
                        text_title.text = value.title
                        text_title_original.text = value.titleOriginal
                        text_status.text = value.status
                        text_publishing.text = value.publishing
                        text_publication.text = value.publication
                        text_year.text = value.year
                        text_chapters.text = value.chapter
                        text_sinopse.text = value.sinopse
                        Glide.with(this@DetailActivity)
                            .load("http://soquadrinhos.com/${value.image}")
                            .into(image_cover)
                    }

                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}