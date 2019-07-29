package com.example.mangavinek.presentation.home.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.core.util.PaginationScroll
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.model.home.entity.Model
import com.example.mangavinek.presentation.home.view.adapter.ItemAdapter
import com.example.mangavinek.presentation.home.view.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jsoup.select.Elements
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.widget.Toast.makeText as makeText1

class HomeFragment : Fragment(), AnkoLogger {

    companion object {
        const val TAG = "HotFragment"
    }

    private val adapterItem: ItemAdapter by lazy {
        ItemAdapter(itemList, context!!)
    }

    private val viewModel by viewModel<NewsViewModel>()

    private var itemList = arrayListOf<Model>()
    private var releasedLoad: Boolean = true
    private var page: Int = 2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.init()

        swipe_refresh.setOnRefreshListener {
            GlobalScope.launch(context = Dispatchers.Main) {
                if (releasedLoad) {
                    delay(1000)
                    page = 2
                    adapterItem.clear(itemList)
                    viewModel.init()
                }
                swipe_refresh.isRefreshing = false
            }
        }

        loadData()
        initUi()
    }

    fun loadData() {
        viewModel.mutableLiveDataHot?.observe(this, Observer<Resource<Elements>> { model ->
            when (model) {
                is Resource.Start -> {
                    screenLoading()
                }
                is Resource.Success -> {
                    populate(model.data)
                    screenSuccess()
                }
                is Resource.Error -> {
                    screenError()
                }
            }
        })
    }

    fun populate(elements: Elements) {
        elements.forEach {
            itemList.add(Model(it))
        }
        adapterItem.notifyItemChanged(itemList.size - 20, itemList.size)
    }

    private fun initUi() {
        with(recycler_view) {
            adapter = adapterItem
            val gridLayoutManager = GridLayoutManager(context, 3)
            addOnScrollListener(object : PaginationScroll(gridLayoutManager) {
                override fun loadMoreItems() {
                    viewModel.init(page++)
                    progress_bottom.visibility = View.VISIBLE
                    releasedLoad = false
                }

                override fun isLoading(): Boolean {
                    return releasedLoad
                }

                override fun hideMoreItems() {
                    progress_bar.visibility = View.GONE
                }
            })
            layoutManager = gridLayoutManager
        }
    }

    fun screenSuccess() {
        progress_bar.visibility = View.GONE
        progress_bottom.visibility = View.GONE
        text_erro.visibility = View.GONE
        releasedLoad = true
    }

    fun screenLoading() {
        progress_bar.visibility = View.VISIBLE
        text_erro.visibility = View.GONE
    }

    fun screenError() {
        text_erro.visibility = View.VISIBLE
        progress_bottom.visibility = View.GONE
        progress_bar.visibility = View.GONE
    }
}
