package com.example.mangavinek.presentation.detail.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.model.detail.entity.DetailChapterResponse
import com.example.mangavinek.presentation.detail.PresentationDetail
import com.example.mangavinek.presentation.detail.presenter.DetailChapterPresenter
import com.example.mangavinek.presentation.detail.view.adapter.ChapterAdapter
import com.example.mangavinek.presentation.detail.view.viewmodel.DetailViewModel
import kotlinx.android.synthetic.main.fragment_detail_chapter.view.*
import kotlinx.android.synthetic.main.loading_screen.*
import org.jetbrains.anko.AnkoLogger
import org.jsoup.select.Elements

class DetailChapterFragment : Fragment(), AnkoLogger, PresentationDetail.ViewChapter {

    private val presenter by lazy {
        presenter()
    }

    private lateinit var adapterChapter: ChapterAdapter
    private var itemList = arrayListOf<DetailChapterResponse>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_detail_chapter, container, false)
        initViewModel()

        initUi(view)
        return view
    }

    override fun populate(elements: Elements) {
        itemList.clear()
        elements.forEach {
            itemList.add(DetailChapterResponse(it))
        }
        adapterChapter.notifyDataSetChanged()
    }

    override fun screenSuccess() {
        constraint_load.visibility = View.GONE
    }

    override fun screenLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun screenError() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun presenter(): PresentationDetail.Presenter {
        return DetailChapterPresenter(this, activity!!)
    }

    override fun viewModel(): DetailViewModel {
        return ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

    private fun initViewModel() {
        val url = arguments?.getString("urlChapter")

        url?.let {
            presenter.apply {
                initViewModel(it)
                loadData()
            }
        }
    }

    private fun initUi(view: View) {
        with(view.recycler_chapter) {
            adapterChapter = ChapterAdapter(itemList, context!!)
            adapter = adapterChapter
            val gridLayoutManager = GridLayoutManager(context, 3)
            layoutManager = gridLayoutManager
        }
    }
}