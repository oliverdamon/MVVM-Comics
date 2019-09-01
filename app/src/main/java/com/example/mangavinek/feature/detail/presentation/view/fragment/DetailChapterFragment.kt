package com.example.mangavinek.feature.detail.presentation.view.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.core.helper.observeResource
import com.example.mangavinek.core.util.maxNumberGridLayout
import com.example.mangavinek.data.model.detail.entity.DetailChapterResponse
import com.example.mangavinek.feature.detail.presentation.view.adapter.ChapterAdapter
import com.example.mangavinek.feature.detail.presentation.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail_chapter.*
import kotlinx.android.synthetic.main.fragment_detail_chapter.view.*
import kotlinx.android.synthetic.main.layout_screen_error.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.browse
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailChapterFragment : Fragment(), AnkoLogger {

    private val viewModel by viewModel<DetailViewModel>()

    private val adapterChapter: ChapterAdapter by lazy {
        ChapterAdapter(itemList) {
            context!!.browse(it.linkChapter)
        }
    }
    private var itemList = arrayListOf<DetailChapterResponse>()
    private var url: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_chapter, container, false)

        loadData()
        initUi(view)
        return view
    }

    private fun loadData() {
        url = arguments?.getString("urlChapter").toString()

        url.let {
            viewModel.fetchList(it)
        }
        viewModel.getListDetailChapter.observeResource(this,
            onSuccess = {
                populate(it)
                showSuccess()
            },
            onError = {
                showError()
            },
            onLoading = {
                showLoading(it)
            })
    }

    private fun populate(listDetailChapterResponse: List<DetailChapterResponse>) {
        itemList.clear()
        itemList.addAll(listDetailChapterResponse)
        adapterChapter.notifyDataSetChanged()
    }

    private fun showSuccess() {
        recycler_chapter.visibility = View.VISIBLE
        include_layout_error.visibility = View.GONE
    }

    private fun showLoading(isVisible: Boolean) {
        include_layout_loading.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showError() {
        include_layout_error.visibility = View.VISIBLE
        recycler_chapter.visibility = View.GONE

        image_refresh_default.setOnClickListener {
            ObjectAnimator.ofFloat(image_refresh_default, View.ROTATION, 0f, 360f).setDuration(300).start()
            itemList.clear()
            viewModel.fetchList(url)
        }
    }

    private fun initUi(view: View) {
        with(view.recycler_chapter) {
            adapter = adapterChapter
            isNestedScrollingEnabled = false
            isFocusable = false
            val gridLayoutManager = GridLayoutManager(context, maxNumberGridLayout(context))
            layoutManager = gridLayoutManager
        }
    }
}