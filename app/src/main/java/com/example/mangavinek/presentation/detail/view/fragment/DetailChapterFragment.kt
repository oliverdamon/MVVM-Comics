package com.example.mangavinek.presentation.detail.view.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.model.detail.entity.DetailChapterResponse
import com.example.mangavinek.presentation.detail.view.adapter.ChapterAdapter
import com.example.mangavinek.presentation.detail.view.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail_chapter.view.*
import org.jetbrains.anko.AnkoLogger
import org.jsoup.select.Elements

class DetailChapterFragment : Fragment(), AnkoLogger {
    private val detailViewModel: DetailViewModel by lazy {
        ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

    private lateinit var adapter: ChapterAdapter
    private var itemList = arrayListOf<DetailChapterResponse>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_chapter, container, false)
        initViewModel()
        detailViewModel.mutableLiveDataChapter?.observe(this, Observer<Resource<Elements>> { model ->
            when (model) {
                is Resource.Start -> {
                }
                is Resource.Success -> {
                    model.data.forEach {
                        itemList.add(DetailChapterResponse(it))
                    }
                    adapter.notifyDataSetChanged()
                }
                is Resource.Error -> {
                }
            }
        })

        initUi(view)
        return view
    }

    fun initViewModel(){
        val url = arguments?.getString("urlChapter")
        url?.let {
            detailViewModel.init2(it)
        }
    }

    private fun initUi(view: View) {
        val activity = activity as Context
        adapter = ChapterAdapter(itemList, activity)
        view.recycler_chapter.adapter = adapter
        val gridLayoutManager = GridLayoutManager(activity, 3)
        view.recycler_chapter.layoutManager = gridLayoutManager
    }
}