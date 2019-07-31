package com.example.mangavinek.presentation.detail.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.core.util.Resource.*
import com.example.mangavinek.model.detail.entity.DetailChapterResponse
import com.example.mangavinek.model.detail.entity.getItems
import com.example.mangavinek.presentation.detail.view.adapter.ChapterAdapter
import com.example.mangavinek.presentation.detail.view.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail_chapter.view.*
import kotlinx.android.synthetic.main.loading_screen.*
import org.jetbrains.anko.AnkoLogger
import org.jsoup.select.Elements
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailChapterFragment : Fragment(), AnkoLogger {

    private val viewModel by viewModel<DetailViewModel>()

    private lateinit var adapterChapter: ChapterAdapter
    private var itemList = arrayListOf<DetailChapterResponse>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_chapter, container, false)
        val url = arguments?.getString("urlChapter")

        url?.let {
            viewModel.initChapter(it)
        }

        loadData()
        initUi(view)
        return view
    }

    private fun loadData() {
        viewModel.mutableLiveDataChapter?.observe(this, Observer<Resource<Elements>> { model ->
            when (model) {
                is Start -> {
                    //screenLoading()
                }
                is Success -> {
                    populate(model.data.getItems())
                    screenSuccess()
                }
                is Error -> {
                    //screenError()
                }
            }
        })
    }

    private fun populate(listDetailChapterResponse: List<DetailChapterResponse>) {
        itemList.clear()
        itemList.addAll(listDetailChapterResponse)
        adapterChapter.notifyDataSetChanged()
    }

    private fun screenSuccess() {
        constraint_load.visibility = View.GONE
    }

    private fun screenLoading() {}

    private fun screenError() {}

    private fun initUi(view: View) {
        with(view.recycler_chapter) {
            adapterChapter = ChapterAdapter(itemList, context!!)
            adapter = adapterChapter
            val gridLayoutManager = GridLayoutManager(context, 3)
            layoutManager = gridLayoutManager
        }
    }
}