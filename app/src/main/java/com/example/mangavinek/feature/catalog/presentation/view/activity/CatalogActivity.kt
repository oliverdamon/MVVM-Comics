package com.example.mangavinek.feature.catalog.presentation.view.activity

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.core.constant.BASE_URL
import com.example.mangavinek.core.helper.PaginationScroll
import com.example.mangavinek.core.helper.observeResource
import com.example.mangavinek.core.util.maxNumberGridLayout
import com.example.mangavinek.data.model.catalog.entity.CatalogResponse
import com.example.mangavinek.feature.catalog.presentation.view.adapter.CatalogAdapter
import com.example.mangavinek.feature.catalog.presentation.viewmodel.CatalogViewModel
import com.example.mangavinek.feature.detail.presentation.view.activity.DetailActivity
import kotlinx.android.synthetic.main.activity_catalog.*
import kotlinx.android.synthetic.main.layout_screen_error.*
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
            if (viewModel.releasedLoad) {
                startActivity<DetailActivity>("url" to BASE_URL.plus(it.link))
            }
        }
    }

    private val viewModel by viewModel<CatalogViewModel>()

    private var itemList = arrayListOf<CatalogResponse>()
    private var url: String = ""
    private var lastPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catalog)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_24dp)

        url = intent.getStringExtra("url")

        swipe_refresh.setOnRefreshListener {
            GlobalScope.launch(context = Dispatchers.Main) {
                if (viewModel.releasedLoad) {
                    delay(1000)
                    include_layout_error.visibility = View.GONE
                    adapterItem.clear(itemList)
                    viewModel.refreshViewModel()
                }
                swipe_refresh.isRefreshing = false
            }
        }

        loadData()
        initUi()
    }

    private fun loadData() {
        viewModel.fetchList(url)
        viewModel.getListCatalog.observeResource(this,
            onSuccess = {
                populate(it)
                showSuccess()
            },
            onError = {
                showError()
            },
            onLoading = {
                showLoading(it)
            })

        viewModel.getLastPagination.observe(this, Observer {
            lastPage = it
        })
    }

    private fun populate(listCatalogResponse: List<CatalogResponse>) {
        itemList.addAll(listCatalogResponse)
        adapterItem.notifyItemChanged(itemList.size - 15, itemList.size)
    }

    private fun initUi() {
        with(recycler_catalog) {
            adapter = adapterItem
            val gridLayoutManager = GridLayoutManager(context, maxNumberGridLayout(context))
            addOnScrollListener(object : PaginationScroll(gridLayoutManager) {
                override fun loadMoreItems() {
                    if (viewModel.currentPage <= lastPage) {
                        viewModel.nextPage()
                        progress_bottom.visibility = View.VISIBLE
                    }
                }

                override fun isLoading(): Boolean {
                    return viewModel.releasedLoad
                }

                override fun hideMoreItems() {
                    include_layout_loading.visibility = View.GONE
                }
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
        progress_bottom.visibility = View.GONE
        include_layout_error.visibility = View.GONE
        viewModel.releasedLoad = true
    }

    private fun showLoading(isVisible: Boolean) {
        include_layout_loading.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showError() {
        include_layout_error.visibility = View.VISIBLE
        progress_bottom.visibility = View.GONE
        recycler_catalog.visibility = View.GONE

        image_refresh_default.setOnClickListener {
            ObjectAnimator.ofFloat(image_refresh_default, View.ROTATION, 0f, 360f).setDuration(300).start()
            if (viewModel.currentPage > 2){
                viewModel.backPreviousPage()
            } else {
                adapterItem.clear(itemList)
                viewModel.refreshViewModel()
            }
        }
    }
}