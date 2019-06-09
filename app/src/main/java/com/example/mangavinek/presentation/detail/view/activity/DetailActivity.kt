package com.example.mangavinek.presentation.detail.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.mangavinek.R
import com.example.mangavinek.core.constant.Constant
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.model.detail.entity.DetailResponse
import com.example.mangavinek.presentation.detail.view.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.AnkoLogger
import org.jsoup.select.Elements

class DetailActivity : AppCompatActivity(), AnkoLogger {
    private val detailViewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val url: String = intent.getStringExtra("url")
        detailViewModel.init(url)

        detailViewModel.mutableLiveData?.observe(this, Observer<Resource<Elements>> { model ->
            when (model) {
                is Resource.Start -> {
                }
                is Resource.Success -> {
                    model.data.let {
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
                            .load(Constant.BASE_URL.plus(value.image))
                            .into(image_cover)
                    }
                }
                is Resource.Error -> {
                }
            }
        })

    }
}