package com.example.mangavinek.feature.catalog.presentation.view.activity

import android.animation.ObjectAnimator
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.core.constant.BASE_URL
import com.example.mangavinek.core.constant.BASE_URL_SEARCH
import com.example.mangavinek.core.helper.PaginationScroll
import com.example.mangavinek.core.helper.observeResource
import com.example.mangavinek.core.util.maxNumberGridLayout
import com.example.mangavinek.feature.catalog.model.domain.CatalogDomain
import com.example.mangavinek.feature.catalog.presentation.view.adapter.CatalogAdapter
import com.example.mangavinek.feature.catalog.presentation.viewmodel.SearchViewModel
import com.example.mangavinek.feature.detail.presentation.view.activity.DetailActivity
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.layout_screen_error.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val adapterItem: CatalogAdapter by lazy {
        CatalogAdapter(listCatalogDomain) {
            if (viewModel.releasedLoad) {
                startActivity<DetailActivity>("url" to BASE_URL.plus(it.url))
            }
        }
    }

    private val viewModel by viewModel<SearchViewModel>()

    private var listCatalogDomain = arrayListOf<CatalogDomain>()
    private var lastPage: Int = 0
    private var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        loadData()
        initUI()
    }

    private fun loadData() {
        viewModel.mutableLiveDataListSearch.observeResource(this,
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

        viewModel.mutableLiveDataLastPagination.observe(this, Observer {
            lastPage = it
        })
    }

    private fun fetchViewModel(url: String) {
        viewModel.currentPage = 2
        lastPage = 0
        adapterItem.clear(listCatalogDomain)
        viewModel.fetchListSearch(url)
    }

    private fun populate(listCatalogDomain: List<CatalogDomain>) {
        this.listCatalogDomain.addAll(listCatalogDomain)
        adapterItem.notifyItemChanged(this.listCatalogDomain.size - 15, this.listCatalogDomain.size)
    }

    private fun initUI() {
        with(recycler_search) {
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val searchItem = menu.findItem(R.id.search)
        searchItem.expandActionView()

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(false)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                if (s.length > 1) {
                    url = String.format(BASE_URL_SEARCH, s)
                    include_layout_error.visibility = View.GONE
                    text_type.visibility = View.GONE
                    fetchViewModel(url)
                }
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })

        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                finish()
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun showSuccess() {
        recycler_search.visibility = View.VISIBLE
        include_layout_loading.visibility = View.GONE
        include_layout_error.visibility = View.GONE
        progress_bottom.visibility = View.GONE
        text_type.visibility = View.GONE
        viewModel.releasedLoad = true

        if (listCatalogDomain.isEmpty()) {
            text_type.visibility = View.VISIBLE
            text_type.text = getString(R.string.type_message_not_comic)
        }
    }

    private fun showLoading() {
        include_layout_loading.visibility = View.VISIBLE
    }

    private fun showError() {
        include_layout_error.visibility = View.VISIBLE
        include_layout_loading.visibility = View.GONE
        progress_bottom.visibility = View.GONE
        recycler_search.visibility = View.GONE
        text_type.visibility = View.GONE

        image_refresh_default.setOnClickListener {
            ObjectAnimator.ofFloat(image_refresh_default, View.ROTATION, 0f, 360f).setDuration(300).start()
            if (viewModel.currentPage > 2){
                viewModel.backPreviousPage()
            } else {
                adapterItem.clear(listCatalogDomain)
                viewModel.refreshViewModel()
            }
        }
    }

}
