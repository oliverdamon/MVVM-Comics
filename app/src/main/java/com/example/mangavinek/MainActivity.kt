package com.example.mangavinek

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import androidx.lifecycle.ViewModelProviders
import org.jetbrains.anko.AnkoLogger
import android.widget.Toast.makeText as makeText1
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.anko.toast
import org.jsoup.select.Elements

class MainActivity : AppCompatActivity(), AnkoLogger {

    private lateinit var adapter: ItemAdapter
    private var itemList2 = arrayListOf<Model>()
    private lateinit var newsViewModel: NewsViewModel
    private lateinit var gridLayoutManager: GridLayoutManager
    private var releasedLoad: Boolean = true
    private var count : Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)
        initViewModel()

        button5.setOnClickListener {
            if(releasedLoad){
                count = 1
                adapter.clear(itemList2)
                newsViewModel.reload("http://soquadrinhos.com/forumdisplay.php?fid=2&page=$count")
                //Toast.makeText(applicationContext, "Se fudeu!", Toast.LENGTH_SHORT).show()
            }
        }
        teste()
        initUi()
    }

    private fun teste() {
        recycler_view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    val visibleItemCount = gridLayoutManager.childCount
                    val totalIntemCount = gridLayoutManager.itemCount
                    val pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition()
                    if (releasedLoad) {
                        if (visibleItemCount + pastVisibleItems >= totalIntemCount) {
                            newsViewModel.reload("http://soquadrinhos.com/forumdisplay.php?fid=2&page=${count++}")
                            releasedLoad = false
                        }
//                        } else if (totalIntemCount == 20) {
//                            count = 2
//                        }
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
                adapter.notifyItemMoved(itemList2.size-20, itemList2.size)
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
        gridLayoutManager = GridLayoutManager(this, 2)
        recycler_view.layoutManager = gridLayoutManager
    }
}
