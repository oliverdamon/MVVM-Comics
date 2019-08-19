package com.example.mangavinek.catalog.presentation.view.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.data.model.catalog.entity.CatalogResponse
import com.example.mangavinek.catalog.presentation.view.adapter.CatalogAdapter
import com.example.mangavinek.catalog.presentation.viewmodel.CatalogViewModel
import com.example.mangavinek.core.constant.BASE_URL
import com.example.mangavinek.core.util.PaginationScroll
import com.example.mangavinek.detail.presentation.view.activity.DetailActivity
import kotlinx.android.synthetic.main.activity_catalog.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatalogActivity : AppCompatActivity(), AnkoLogger {

    private val adapterItem: CatalogAdapter by lazy {
        CatalogAdapter(itemList) {
            startActivity<DetailActivity>("url" to BASE_URL.plus(it.link))
        }
    }

    private val viewModel by viewModel<CatalogViewModel>()

    private var itemList = arrayListOf<CatalogResponse>()
    private var releasedLoad: Boolean = true
    private var page: Int = 2
    private var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)
        url = intent.getStringExtra("url")

        swipe_refresh.setOnRefreshListener {
            GlobalScope.launch(context = Dispatchers.Main) {
                if (releasedLoad) {
                    delay(1000)
                    page = 2
                    text_erro.visibility = View.GONE
                    adapterItem.clear(itemList)
                    viewModel.fetchList(url)
                }
                swipe_refresh.isRefreshing = false
            }
        }

        loadData()
        initUi()
    }

    private fun loadData() {
        viewModel.fetchList(url)
        viewModel.getListCatalog.observe(this,
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

    private fun lastPage(): Int{
        var page = 0
        viewModel.getLastPagination.observe(this, Observer { page = it })

        return page
    }

    private fun populate(listCatalogResponse: List<CatalogResponse>) {
        itemList.addAll(listCatalogResponse)
        adapterItem.notifyItemChanged(itemList.size - 15, itemList.size)
    }

    private fun initUi() {
        with(recycler_catalog) {
            adapter = adapterItem
            val gridLayoutManager = GridLayoutManager(context, 3)
            addOnScrollListener(object : PaginationScroll(gridLayoutManager) {
                override fun loadMoreItems() {
                    if (page <= lastPage()){
                        viewModel.fetchList(url, page++)
                        progress_bottom.visibility = View.VISIBLE
                        releasedLoad = false
                    }
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
        recycler_catalog.visibility = View.VISIBLE
        progress_bottom.visibility = View.GONE
        text_erro.visibility = View.GONE
        releasedLoad = true
    }

    private fun showLoading(isVisible: Boolean) {
        progress_bar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showError() {
        text_erro.visibility = View.VISIBLE
        progress_bottom.visibility = View.GONE
        recycler_catalog.visibility = View.GONE
    }
}