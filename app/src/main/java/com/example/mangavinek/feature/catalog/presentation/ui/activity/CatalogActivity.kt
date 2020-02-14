package com.example.mangavinek.feature.catalog.presentation.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.core.helper.AdapterPagination
import com.example.mangavinek.core.constant.BASE_URL
import com.example.mangavinek.core.helper.addPaginationScroll
import com.example.mangavinek.core.helper.observeResource
import com.example.mangavinek.core.util.maxNumberGridLayout
import com.example.mangavinek.feature.catalog.model.domain.CatalogDomain
import com.example.mangavinek.feature.catalog.presentation.ui.viewholder.CatalogViewHolder
import com.example.mangavinek.feature.catalog.presentation.viewmodel.CatalogViewModel
import com.example.mangavinek.feature.detail.presentation.ui.activity.DetailActivity
import com.example.mangavinek.core.helper.startActivity
import kotlinx.android.synthetic.main.activity_catalog.*
import kotlinx.android.synthetic.main.activity_catalog.include_layout_error
import kotlinx.android.synthetic.main.activity_catalog.include_layout_loading
import kotlinx.android.synthetic.main.activity_catalog.swipe_refresh
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CatalogActivity : AppCompatActivity() {

    private val adapterCatalog: AdapterPagination by lazy {
        AdapterPagination(
            getLayout = R.layout.row_data,

            viewHolder = {
                CatalogViewHolder(it, onItemClickListener = { item ->
                    startActivity<DetailActivity>("url" to BASE_URL.plus(item.url))
                })
            },

            onRetryClickListener = {
                errorBottomScroll(false)
                viewModel.backPreviousPage()
            })
    }

    private val viewModel by viewModel<CatalogViewModel>{
        parametersOf(url)
    }

    private var url: String = ""
    private var lastPage: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_24dp)

        url = intent.getStringExtra("url")

        swipeRefresh()

        loadData()
        initUI()
    }

    private fun loadData() {
        viewModel.getLiveDataListCatalog.observeResource(this,
            onSuccess = {
                populate(it)
                showSuccess()
            },
            onError = {
                showError()
            },
            onLoading = {
                showLoading()
            })

        viewModel.getLiveDataLastPagination.observe(this, Observer {
            lastPage = it
        })
    }

    private fun populate(listCatalogDomain: List<CatalogDomain>) {
        adapterCatalog.addList(listCatalogDomain)
        pagingFinished()
    }

    private fun refresh() {
        adapterCatalog.clearList()
        viewModel.refreshViewModel()
    }

    private fun swipeRefresh() {
        swipe_refresh.setOnRefreshListener {
            GlobalScope.launch(context = Dispatchers.Main) {
                delay(1000)
                refresh()
                swipe_refresh.isRefreshing = false
            }
        }
    }

    private fun initUI() {
        with(recycler_catalog) {
            adapter = adapterCatalog
            val gridLayoutManager = GridLayoutManager(context, maxNumberGridLayout(context))
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapterCatalog.getItemViewType(position)) {
                        AdapterPagination.ITEM_LIST -> 1
                        AdapterPagination.ITEM_BOTTOM -> gridLayoutManager.spanCount
                        else -> gridLayoutManager.spanCount
                    }
                }
            }
            addPaginationScroll(gridLayoutManager,
                loadMoreItems = {
                    viewModel.nextPage()
                },
                isLoading = {
                    viewModel.releasedLoad
                },
                isLastPage = {
                    viewModel.lastPage
                },
                hideOthersItems = {
                    include_layout_loading.visibility = View.GONE
                })

            layoutManager = gridLayoutManager
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showSuccess() {
        recycler_catalog.visibility = View.VISIBLE
        include_layout_loading.visibility = View.GONE
        include_layout_error.visibility = View.GONE
    }

    private fun showLoading() {
        if (viewModel.releasedLoad) {
            include_layout_loading.visibility = View.VISIBLE
        }
    }

    private fun showError(){
        if (viewModel.currentPage > 1) {
            errorBottomScroll(true)
        } else {
            showErrorFullScreen()
        }
    }

    private fun showErrorFullScreen() {
        include_layout_error.visibility = View.VISIBLE
        include_layout_loading.visibility = View.GONE
        recycler_catalog.visibility = View.GONE
        viewModel.refreshViewModel()
    }

    private fun errorBottomScroll(showError: Boolean) {
        adapterCatalog.showErrorRetry(showError)
    }

    private fun pagingFinished(){
        if (viewModel.currentPage == lastPage || lastPage == null) {
            adapterCatalog.removeItemBottom()
            viewModel.pagingFinished()
        }
    }
}