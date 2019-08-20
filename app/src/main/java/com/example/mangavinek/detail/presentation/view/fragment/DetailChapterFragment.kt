package com.example.mangavinek.detail.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.detail.model.domain.entity.DetailChapterResponse
import com.example.mangavinek.detail.presentation.view.adapter.ChapterAdapter
import com.example.mangavinek.detail.presentation.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail_chapter.view.*
import kotlinx.android.synthetic.main.loading_screen.*
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_chapter, container, false)

        loadData()
        initUi(view)
        return view
    }

    private fun loadData() {
        val url = arguments?.getString("urlChapter")

        url?.let {
            viewModel.fetchList(it)
        }
        viewModel.getListDetailChapter.observe(this, Observer { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    resource.data?.let {
                        populate(it)
                        showSuccess()
                    }
                }
                Resource.Status.ERROR -> {
                    showError()
                }
                Resource.Status.LOADING -> {
                    resource.boolean?.let { showLoading(it) }
                }
            }
        })
    }

    private fun populate(listDetailChapterResponse: List<DetailChapterResponse>) {
        itemList.clear()
        itemList.addAll(listDetailChapterResponse)
        adapterChapter.notifyDataSetChanged()
    }

    private fun showSuccess() {
        constraint_load.visibility = View.GONE
    }

    private fun showLoading(isVisible: Boolean) {}

    private fun showError() {}

    private fun initUi(view: View) {
        with(view.recycler_chapter) {
            adapter = adapterChapter
            isNestedScrollingEnabled = false
            isFocusable = false
            val gridLayoutManager = GridLayoutManager(context, 3)
            layoutManager = gridLayoutManager
        }
    }
}