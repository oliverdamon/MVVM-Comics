package com.example.mangavinek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.ViewModelProviders
import org.jetbrains.anko.AnkoLogger
import android.widget.Toast.makeText as makeText1
import com.example.mangavinek.core.util.PaginationScroll
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.model.home.entity.Model
import com.example.mangavinek.presentation.home.view.adapter.ItemAdapter
import com.example.mangavinek.presentation.home.view.viewmodel.NewsViewModel
import org.jsoup.select.Elements
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var adapter: ItemAdapter
    private var itemList = arrayListOf<Model>()
    private lateinit var newsViewModel: NewsViewModel
    private var releasedLoad: Boolean = true
    private var page : Int = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        newsViewModel.init()
        initViewModel()

        swipe_refresh.setOnRefreshListener{
            GlobalScope.launch(context = Dispatchers.Main) {
                if(releasedLoad){
                    delay(1000)
                    page = 2
                    adapter.clear(itemList)
                    newsViewModel.init()
                }
                swipe_refresh.isRefreshing = false
            }
        }

        initUi()
    }

    private fun initViewModel() {
        newsViewModel.mutableLiveDataHot?.observe(this, Observer<Resource<Elements>> { model ->
            when (model) {
                is Resource.Start -> {
                    screenLoading()
                }
                is Resource.Success -> {
                    model.data.forEach {
                        itemList.add(Model(it))
                    }
                    adapter.notifyItemChanged(itemList.size - 20, itemList.size)
                    screenSuccess()
                }
                is Resource.Error -> {
                    screenError()
                }
            }
        })
    }

    private fun initUi() {
        adapter = ItemAdapter(itemList, this)
        recycler_view.adapter = adapter
        val gridLayoutManager = GridLayoutManager(this, 3)
        recycler_view.addOnScrollListener(object : PaginationScroll(gridLayoutManager) {
            override fun loadMoreItems() {
                newsViewModel.init(page++)
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
        recycler_view.layoutManager = gridLayoutManager
    }

    private fun screenSuccess(){
        progress_bar.visibility = View.GONE
        progress_bottom.visibility = View.GONE
        text_erro.visibility = View.GONE
        releasedLoad = true
    }

    private fun screenLoading(){
        progress_bar.visibility = View.VISIBLE
        text_erro.visibility = View.GONE
    }

    private fun screenError(){
        text_erro.visibility = View.VISIBLE
        progress_bottom.visibility = View.GONE
        progress_bar.visibility = View.GONE
    }
}
