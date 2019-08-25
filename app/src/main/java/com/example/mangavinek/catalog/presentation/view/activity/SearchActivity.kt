package com.example.mangavinek.catalog.presentation.view.activity

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
import com.example.mangavinek.catalog.presentation.view.adapter.CatalogAdapter
import com.example.mangavinek.catalog.presentation.viewmodel.CatalogViewModel
import com.example.mangavinek.core.constant.BASE_URL
import com.example.mangavinek.core.constant.BASE_URL_SEARCH
import com.example.mangavinek.core.helper.PaginationScroll
import com.example.mangavinek.core.helper.observeResource
import com.example.mangavinek.data.model.catalog.entity.CatalogResponse
import com.example.mangavinek.detail.presentation.view.activity.DetailActivity
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val adapterItem: CatalogAdapter by lazy {
        CatalogAdapter(itemList) {
            startActivity<DetailActivity>("url" to BASE_URL.plus(it.link))
        }
    }

    private val viewModel by viewModel<CatalogViewModel>()

    private var itemList = arrayListOf<CatalogResponse>()
    private var releasedLoad: Boolean = true
    private var page: Int = 2
    private var lastPage: Int = 0
    private var url: String = ""
    private var textType: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        loadData()
        initUi()
    }

    private fun loadData() {
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

    private fun fetchViewModel(url: String) {
        page = 2
        lastPage = 0
        adapterItem.clear(itemList)
        viewModel.fetchList(url)
    }

    private fun populate(listCatalogResponse: List<CatalogResponse>) {
        itemList.addAll(listCatalogResponse)
        adapterItem.notifyItemChanged(itemList.size - 15, itemList.size)
    }

    private fun initUi() {
        with(recycler_search) {
            adapter = adapterItem
            val gridLayoutManager = GridLayoutManager(context, 3)
            addOnScrollListener(object : PaginationScroll(gridLayoutManager) {
                override fun loadMoreItems() {
                    if (page <= lastPage) {
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
        progress_bottom.visibility = View.GONE
        text_type.visibility = View.GONE
        releasedLoad = true

        if (itemList.isEmpty()) {
            textType = "Esse título não existe."
            text_type.visibility = View.VISIBLE
            text_type.text = textType
        }
    }

    private fun showLoading(isVisible: Boolean) {
        progress_bar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showError() {
        text_type.visibility = View.VISIBLE
        progress_bottom.visibility = View.GONE
        recycler_search.visibility = View.GONE

        textType = "Error."
        text_type.text = textType
    }

}
