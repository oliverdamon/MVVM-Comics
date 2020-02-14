package com.example.mangavinek.presentation.catalog.ui.activity

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
import com.example.mangavinek.core.helper.AdapterPagination
import com.example.mangavinek.core.helper.addPaginationScroll
import com.example.mangavinek.core.helper.observeResource
import com.example.mangavinek.core.helper.startActivity
import com.example.mangavinek.core.util.maxNumberGridLayout
import com.example.mangavinek.model.catalog.domain.CatalogDomain
import com.example.mangavinek.presentation.catalog.ui.viewholder.CatalogViewHolder
import com.example.mangavinek.presentation.catalog.viewmodel.SearchViewModel
import com.example.mangavinek.presentation.detail.ui.activity.DetailActivity
import kotlinx.android.synthetic.main.activity_catalog.*
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.activity_search.include_layout_error
import kotlinx.android.synthetic.main.activity_search.include_layout_loading
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val adapterItem: AdapterPagination by lazy {
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

    private val viewModel by viewModel<SearchViewModel>()

    private var lastPage: Int? = 0
    private var url: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        loadData()
        initUI()
    }

    private fun loadData() {
        viewModel.getLiveDataListSearch.observeResource(this,
            onSuccess = {
                populate(it)
                showSuccess(it)
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

    private fun fetchViewModel(url: String) {
        viewModel.currentPage = 1
        adapterItem.clearList()
        viewModel.fetchListSearch(url)
    }

    private fun populate(listCatalogDomain: List<CatalogDomain>) {
        adapterItem.addList(listCatalogDomain)
        pagingFinished()
    }

    private fun initUI() {
        with(recycler_search) {
            adapter = adapterItem
            val gridLayoutManager = GridLayoutManager(context, maxNumberGridLayout(context))
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapterItem.getItemViewType(position)) {
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
                hideOthersItems = {
                    include_layout_loading.visibility = View.GONE
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

    private fun showSuccess(listCatalogDomain: List<CatalogDomain>) {
        recycler_search.visibility = View.VISIBLE
        include_layout_loading.visibility = View.GONE
        include_layout_error.visibility = View.GONE
        text_type.visibility = View.GONE

        if (listCatalogDomain.isEmpty()) {
            text_type.visibility = View.VISIBLE
            text_type.text = getString(R.string.type_message_not_comic)
        }
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
        adapterItem.showErrorRetry(showError)
    }

    private fun pagingFinished(){
        if (viewModel.currentPage == lastPage || lastPage == null) {
            adapterItem.removeItemBottom()
            viewModel.pagingFinished()
        }
    }
}
