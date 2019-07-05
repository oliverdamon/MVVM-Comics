package com.example.mangavinek.presentation.detail.view.activity

import android.annotation.SuppressLint
import android.graphics.Color
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
import kotlinx.android.synthetic.main.activity_detail.image_cover
import kotlinx.android.synthetic.main.activity_detail.text_publication
import kotlinx.android.synthetic.main.activity_detail.text_publishing
import kotlinx.android.synthetic.main.activity_detail.text_sinopse
import kotlinx.android.synthetic.main.activity_detail.text_status
import kotlinx.android.synthetic.main.activity_detail.text_title
import kotlinx.android.synthetic.main.activity_detail.text_title_original
import kotlinx.android.synthetic.main.activity_detail.text_year
import org.jetbrains.anko.AnkoLogger
import org.jsoup.select.Elements
import android.text.Spannable
import android.graphics.Typeface
import android.text.style.StyleSpan
import android.text.SpannableString
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.model.detail.entity.StatusChapter
import com.example.mangavinek.presentation.detail.view.adapter.StatusChapterAdapter
import com.example.mangavinek.presentation.detail.view.fragment.DetailChapterFragment
import kotlinx.android.synthetic.main.loading_screen.*

class DetailActivity : AppCompatActivity(), AnkoLogger {
    private val detailViewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

    private lateinit var adapter: StatusChapterAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val url: String = intent.getStringExtra("url")
        detailViewModel.init(url)

        detailViewModel.mutableLiveData?.observe(this, Observer<Resource<Elements>> { model ->
            when (model) {
                is Resource.Start -> {
                    constraint_load.visibility = View.VISIBLE
                    scroll_view.visibility = View.GONE
                }
                is Resource.Success -> {
                    scroll_view.visibility = View.VISIBLE
                    constraint_load.visibility = View.GONE
                    populateDetail(DetailResponse(model.data[0]))
                }
                is Resource.Error -> {
                    scroll_view.visibility = View.GONE
                    constraint_load.visibility = View.GONE
                }
            }
        })

    }

    private fun populateDetail(detailResponse: DetailResponse?) {
        detailResponse?.let {
            text_title.text = it.title
            text_title_original.text = getStringBold("Nome original: ${it.titleOriginal}", "Nome original:")
            text_status.text = detailResponse.status
            text_publishing.text = getStringBold("Editora: ${it.publishing}", "Editora:")
            text_publication.text = getStringBold("Publicação: ${it.publication}", "Publicação:")
            text_year.text = getStringBold("Volume: ${it.year}", "Volume:")
            text_sinopse.text = it.sinopse
            val urlImage = Constant.BASE_URL.plus(it.image)

            Glide.with(this@DetailActivity)
                .load(urlImage)
                .into(image_cover)

            Glide.with(this@DetailActivity)
                .load(urlImage)
                .into(image_cover_complet)

            initRecycler(mergeStatusList(it))
            initFragment(it)
        }
    }

    private fun mergeStatusList(it: DetailResponse): MutableList<StatusChapter> {
        val partsAvailable = it.issueAvailable.split(" ".toRegex())
        val partsTranslated = it.issueTranslated.split(" ".toRegex())
        val partsUnavailable = it.issueUnavailable.split(" ".toRegex())
        val mutableList = mutableListOf<StatusChapter>()

        partsAvailable.forEach {
            if (it.isNotEmpty()) {
                val statusChapter = StatusChapter("available", it)
                mutableList.add(statusChapter)
            }
        }

        partsTranslated.forEach {
            if (it.isNotEmpty()) {
                val statusChapter = StatusChapter("translated", it)
                mutableList.add(statusChapter)
            }
        }

        partsUnavailable.forEach {
            if (it.isNotEmpty()) {
                val statusChapter = StatusChapter("unavailable", it)
                mutableList.add(statusChapter)
            }
        }

        mutableList.sortWith(compareBy(StatusChapter::number))

        return mutableList
    }

    private fun getStringBold(textComplete: String, textSpecific: String): SpannableString {
        val spannableString = SpannableString(textComplete)
        spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, textSpecific.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }

    private fun initFragment(detailResponse: DetailResponse?) {
        detailResponse?.let {
            val fragment = DetailChapterFragment()
            val args = Bundle()
            args.putString("urlChapter", it.link)
            fragment.arguments = args
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()
            transaction.replace(R.id.frame_chapter, fragment)
                .commit()
        }
        buttonShowChapter.setOnClickListener {
            checkButton(buttonShowChapter, buttonShowStatus, true)
        }

        buttonShowStatus.setOnClickListener {
            checkButton(buttonShowStatus, buttonShowChapter, false)
        }
    }

    @SuppressLint("PrivateResource")
    private fun checkButton(checked: Button, unChecked: Button, isCheck: Boolean) {
        checked.setBackgroundColor(ContextCompat.getColor(this, R.color.background_material_light))
        checked.setTextColor(Color.parseColor("#FF424242"))

        unChecked.setBackgroundColor(Color.parseColor("#FF424242"))
        unChecked.setTextColor(ContextCompat.getColor(this, R.color.material_grey_600))

        frame_chapter.visibility = if (isCheck) View.VISIBLE else View.GONE
        recycler_chapter_status.visibility = if (isCheck) View.GONE else View.VISIBLE
    }

    private fun initRecycler(list: List<StatusChapter>) {
        adapter = StatusChapterAdapter(list, this)
        recycler_chapter_status.adapter = adapter
        val gridLayoutManager = GridLayoutManager(this, 4)
        recycler_chapter_status.layoutManager = gridLayoutManager
    }
}