package com.example.mangavinek.presentation.detail.view.fragment

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mangavinek.R
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.model.detail.entity.DetailResponse
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
    private var itemList = arrayListOf<DetailResponse>()
    private lateinit var recyclerView: RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_chapter, container, false)
        detailViewModel.init2("http://hqs-soquadrinhos.blogspot.com/2018/08/as-aventuras-dos-superfilhos-2018.html")
        detailViewModel.mutableLiveDataChapter?.observe(this, Observer<Resource<Elements>> { model ->
            when (model) {
                is Resource.Start -> {
                }
                is Resource.Success -> {
                    model.data.forEach {
                        itemList.add(DetailResponse(it))
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

    private fun initUi(view: View) {
        adapter = ChapterAdapter(itemList, this.context!!)
        view.recycler_chapter.adapter = adapter
        val gridLayoutManager = GridLayoutManager(this.context, 3)
        view.recycler_chapter.layoutManager = gridLayoutManager
    }
}