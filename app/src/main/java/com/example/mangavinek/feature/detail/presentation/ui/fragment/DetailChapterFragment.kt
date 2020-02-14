package com.example.mangavinek.feature.detail.presentation.ui.fragment

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
import com.example.mangavinek.feature.detail.model.domain.DetailChapterDomain
import com.example.mangavinek.feature.detail.presentation.ui.adapter.ChapterAdapter
import com.example.mangavinek.feature.detail.presentation.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail_chapter.*
import kotlinx.android.synthetic.main.fragment_detail_chapter.view.*
import kotlinx.android.synthetic.main.layout_screen_error.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.browse
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailChapterFragment : Fragment(), AnkoLogger {

    private val viewModel by viewModel<DetailViewModel>{
        parametersOf(url)
    }

    private val adapterChapter: ChapterAdapter by lazy {
        ChapterAdapter(listDetailChapterDomain) {
            context!!.browse(it.url)
        }
    }
    private var listDetailChapterDomain = arrayListOf<DetailChapterDomain>()
    private var url: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_chapter, container, false)

        initUi(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
    }

    private fun loadData() {
        url = arguments?.getString("urlChapter").toString()

        viewModel.getLiveDataListDetailChapter.observeResource(viewLifecycleOwner,
            onSuccess = {
                populate(it)
                showSuccess()
            },
            onError = {
                showError()
            },
            onLoading = {
                showLoading()
            })
    }

    private fun populate(listDetailChapterDomain: List<DetailChapterDomain>) {
        this.listDetailChapterDomain.clear()
        this.listDetailChapterDomain.addAll(listDetailChapterDomain)
        adapterChapter.notifyDataSetChanged()
    }

    private fun showSuccess() {
        recycler_chapter.visibility = View.VISIBLE
        include_layout_loading.visibility = View.GONE
        include_layout_error.visibility = View.GONE
    }

    private fun showLoading() {
        include_layout_loading.visibility = View.VISIBLE
    }

    private fun showError() {
        include_layout_error.visibility = View.VISIBLE
        include_layout_loading.visibility = View.GONE
        recycler_chapter.visibility = View.GONE

        image_refresh_default.setOnClickListener {
            ObjectAnimator.ofFloat(image_refresh_default, View.ROTATION, 0f, 360f).setDuration(300).start()
            listDetailChapterDomain.clear()
            viewModel.fetchListDetailChapter(url)
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