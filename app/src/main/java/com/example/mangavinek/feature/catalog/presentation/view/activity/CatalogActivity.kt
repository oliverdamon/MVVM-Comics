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
import com.example.mangavinek.feature.catalog.model.domain.CatalogDomain
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
import org.koin.core.parameter.parametersOf

class CatalogActivity : AppCompatActivity(), AnkoLogger {

    private val adapterCatalog: CatalogAdapter by lazy {
        CatalogAdapter(listCatalogDomain) {
            if (viewModel.releasedLoad) {
                startActivity<DetailActivity>("url" to BASE_URL.plus(it.url))
            }
        }
    }

    private val viewModel by viewModel<CatalogViewModel>{
        parametersOf(url)
    }

    private var listCatalogDomain = arrayListOf<CatalogDomain>()
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
                    adapterCatalog.clear(listCatalogDomain)
                    viewModel.refreshViewModel()
                }
                swipe_refresh.isRefreshing = false
            }
        }

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
        this.listCatalogDomain.addAll(listCatalogDomain)
        adapterCatalog.notifyItemChanged(this.listCatalogDomain.size - 15, this.listCatalogDomain.size)
    }

    private fun initUI() {
        with(recycler_catalog) {
            adapter = adapterCatalog
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
        include_layout_loading.visibility = View.GONE
        include_layout_error.visibility = View.GONE
        progress_bottom.visibility = View.GONE
        viewModel.releasedLoad = true
    }

    private fun showLoading() {
        include_layout_loading.visibility = View.VISIBLE
    }

    private fun showError() {
        include_layout_error.visibility = View.VISIBLE
        include_layout_loading.visibility = View.GONE
        recycler_catalog.visibility = View.GONE
        progress_bottom.visibility = View.GONE

        image_refresh_default.setOnClickListener {
            ObjectAnimator.ofFloat(image_refresh_default, View.ROTATION, 0f, 360f).setDuration(300).start()
            if (viewModel.currentPage > 1){
                viewModel.backPreviousPage()
            } else {
                adapterCatalog.clear(listCatalogDomain)
                viewModel.refreshViewModel()
            }
        }
    }
}