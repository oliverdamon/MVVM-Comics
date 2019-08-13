package com.example.mangavinek.detail.presentation.view.activity

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.mangavinek.R
import com.example.mangavinek.core.constant.BASE_URL
import com.example.mangavinek.detail.model.domain.entity.DetailResponse
import com.example.mangavinek.detail.model.domain.entity.StatusChapter
import com.example.mangavinek.detail.presentation.view.adapter.StatusChapterAdapter
import com.example.mangavinek.detail.presentation.view.fragment.DetailChapterFragment
import com.example.mangavinek.detail.presentation.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.layout_detail.*
import org.jetbrains.anko.AnkoLogger
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity(), AnkoLogger {

    private val viewModel by viewModel<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        loadData()
    }

    private fun loadData() {
        val url: String = intent.getStringExtra("url")
        viewModel.fetchDetail(url)
        viewModel.getDetail.observe(this,
            onSuccess = {
                populateDetail(it)
                showSuccess()
            },
            onLoading = {
                showLoading(it)
            },
            onError = {
                showError()
            }
        )
    }

    private fun populateDetail(detailResponse: DetailResponse?) {
        detailResponse?.let {
            text_title.text = it.title
            text_title_original.text = getStringBold("Nome original: ${it.titleOriginal}",
                "Nome original:")
            text_status.text = detailResponse.status
            text_publishing.text = getStringBold("Editora: ${it.publishing}", "Editora:")
            text_publication.text = getStringBold("Publicação: ${it.publication}", "Publicação:")
            text_year.text = getStringBold("Volume: ${it.year}", "Volume:")
            text_sinopse.text = it.sinopse
            val urlImage = BASE_URL.plus(it.image)

            Glide.with(this@DetailActivity)
                .load(urlImage)
                .into(image_cover)

            Glide.with(this@DetailActivity)
                .load(urlImage)
                .into(image_cover_complet)

            initRecycler(it)
            initFragment(it.link)
        }
    }

    private fun showSuccess() {
        layout_detail.visibility = View.VISIBLE
        layout_loading.visibility = View.GONE
    }

    private fun showLoading(isVisible: Boolean) {}

    private fun showError() {}

    private fun mergeStatusList(): List<StatusChapter> {
        var list = listOf<StatusChapter>()
        viewModel.mergeStatusList.observe(this, Observer { list = it })
        return list
    }

    private fun initRecycler(detailResponse: DetailResponse) {
        viewModel.fetchListStatus(detailResponse)
        with(recycler_chapter_status) {
            adapter = StatusChapterAdapter(mergeStatusList())
            val gridLayoutManager = GridLayoutManager(this@DetailActivity, 4)
            layoutManager = gridLayoutManager
        }
    }

    private fun getStringBold(textComplete: String, textSpecific: String): SpannableString {
        val spannableString = SpannableString(textComplete)
        spannableString.setSpan(StyleSpan(Typeface.BOLD), 0, textSpecific.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannableString
    }

    private fun initFragment(link: String) {
        val fragment = DetailChapterFragment()
        val args = Bundle()
        args.putString("urlChapter", link)
        fragment.arguments = args

        supportFragmentManager.inTransaction {
            add(R.id.frame_chapter, fragment)

        }
        buttonShowChapter.setOnClickListener {
            checkButton(checked = buttonShowChapter, unChecked = buttonShowStatus, isCheck = true)
        }

        buttonShowStatus.setOnClickListener {
            checkButton(checked = buttonShowStatus, unChecked = buttonShowChapter, isCheck = false)
        }
    }

    private inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().func().commit()
    }

    @SuppressLint("PrivateResource")
    private fun checkButton(checked: Button, unChecked: Button, isCheck: Boolean) {
        checked.setBackgroundColor(ContextCompat.getColor(this, R.color.background_material_light))
        checked.setTextColor(ContextCompat.getColor(this, R.color.background_grey))

        unChecked.setBackgroundColor(ContextCompat.getColor(this, R.color.background_grey))
        unChecked.setTextColor(ContextCompat.getColor(this, R.color.dark_grey))

        frame_chapter.visibility = if (isCheck) View.VISIBLE else View.GONE
        recycler_chapter_status.visibility = if (isCheck) View.GONE else View.VISIBLE
    }
}