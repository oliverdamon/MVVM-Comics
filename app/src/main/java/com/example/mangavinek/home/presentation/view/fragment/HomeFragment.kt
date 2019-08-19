package com.example.mangavinek.home.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.core.constant.BASE_URL
import com.example.mangavinek.core.util.PaginationScroll
import com.example.mangavinek.detail.presentation.view.activity.DetailActivity
import com.example.mangavinek.home.model.domain.entity.HomeResponse
import com.example.mangavinek.home.presentation.view.adapter.HomeAdapter
import com.example.mangavinek.home.presentation.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.widget.Toast.makeText as makeText1

class HomeFragment : Fragment(), AnkoLogger {

    companion object {
        const val TAG = "HomeFragment"
    }

    private val adapterHome: HomeAdapter by lazy {
        HomeAdapter(itemList) {
            context!!.startActivity<DetailActivity>("url" to BASE_URL.plus(it.link))
        }
    }

    private val viewModel by viewModel<HomeViewModel>()

    private var itemList = arrayListOf<HomeResponse>()
    private var releasedLoad: Boolean = true
    private var page: Int = 2

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh.setOnRefreshListener {
            GlobalScope.launch(context = Dispatchers.Main) {
                if (releasedLoad) {
                    delay(1000)
                    page = 2
                    text_erro.visibility = View.GONE
                    adapterHome.clear(itemList)
                    viewModel.fetchList()
                }
                swipe_refresh.isRefreshing = false
            }
        }

        loadData()
        initUi()
    }

    private fun loadData() {
        viewModel.fetchList()
        viewModel.getList.observe(this,
            onSuccess = {
                populate(it)
                showSuccess()
            },
            onLoading = {
                showLoading(it)
            },
            onError = {
                showError()
            }
        )
    }

    private fun populate(listHomeResponse: List<HomeResponse>) {
        itemList.addAll(listHomeResponse)
        adapterHome.notifyItemChanged(itemList.size - 20, itemList.size)
    }

    private fun initUi() {
        with(recycler_view) {
            adapter = adapterHome
            val gridLayoutManager = GridLayoutManager(context, 3)
            addOnScrollListener(object : PaginationScroll(gridLayoutManager) {
                override fun loadMoreItems() {
                    viewModel.fetchList(page++)
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

    private fun showSuccess() {
        recycler_view.visibility = View.VISIBLE
        progress_bottom.visibility = View.GONE
        releasedLoad = true
    }

    private fun showLoading(isVisible: Boolean) {
        progress_bar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showError() {
        text_erro.visibility = View.VISIBLE
        progress_bottom.visibility = View.GONE
        recycler_view.visibility = View.GONE
    }
}