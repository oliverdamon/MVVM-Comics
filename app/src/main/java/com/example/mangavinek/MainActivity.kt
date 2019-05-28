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
import androidx.recyclerview.widget.RecyclerView
import org.jsoup.select.Elements

class MainActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var adapter: ItemAdapter
    private var itemList2 = arrayListOf<Model>()
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var gridLayoutManager: GridLayoutManager
    private var releasedLoad: Boolean? = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        initViewModel()

        button5.setOnClickListener {
            adapter.clear(itemList2)
            newsViewModel.reload("http://soquadrinhos.com/forumdisplay.php?fid=2")
        }
        teste()
        initUi()
    }

    private fun teste() {
        var count = 2
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = gridLayoutManager.childCount
                    val totalIntemCount = gridLayoutManager.itemCount
                    val pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition()
                    if (releasedLoad == true) {
                        if (visibleItemCount + pastVisibleItems >= totalIntemCount) {
                            newsViewModel.reload("http://soquadrinhos.com/forumdisplay.php?fid=2&page=${count++}")
                            releasedLoad = false
                        } else if (totalIntemCount == 20) {
                            count = 2
                        }
                    }
                }
            }
        })
    }

    private val stateObserverHot = Observer<Resource<Elements>> { model ->
        when (model) {
            is Resource.Start -> {
                progress_bar.visibility = View.VISIBLE
                text_erro.visibility = View.GONE
            }
            is Resource.Success -> {
                model.data.forEach {
                    itemList2.add(Model(it))
                }
                adapter.notifyDataSetChanged()
                releasedLoad = true
                progress_bar.visibility = View.GONE
                text_erro.visibility = View.GONE
            }
            is Resource.Error -> {
                progress_bar.visibility = View.GONE
                text_erro.visibility = View.VISIBLE
            }
        }
    }

    private fun initViewModel() {
        newsViewModel.mutableLiveDataHot?.observe(this, stateObserverHot)
    }

    private fun initUi() {
        adapter = ItemAdapter(itemList2, this)
        recycler_view.adapter = adapter
        recycler_view.hasFixedSize()
        gridLayoutManager = GridLayoutManager(this, 3)
        recycler_view.layoutManager = gridLayoutManager
    }
}
