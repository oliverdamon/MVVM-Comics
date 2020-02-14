package com.example.mangavinek.presentation.home.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.core.helper.AdapterPagination.Companion.ITEM_BOTTOM
import com.example.mangavinek.core.helper.AdapterPagination.Companion.ITEM_LIST
import com.example.mangavinek.core.constant.BASE_URL
import com.example.mangavinek.core.helper.*
import com.example.mangavinek.core.util.maxNumberGridLayout
import com.example.mangavinek.presentation.detail.ui.activity.DetailActivity
import com.example.mangavinek.model.home.domain.NewChapterDomain
import com.example.mangavinek.presentation.home.ui.viewholder.HomeViewHolder
import com.example.mangavinek.presentation.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    companion object {
        const val TAG = "HomeFragment"
    }

    private val adapterHome: AdapterPagination by lazy {
        AdapterPagination(
            getLayout = R.layout.row_data,

            viewHolder = {
                HomeViewHolder(it, onItemClickListener = { item ->
                    context!!.startActivity<DetailActivity>("url" to BASE_URL.plus(item.url))
                })
            },

            onRetryClickListener = {
                errorBottomScroll(false)
                viewModel.backPreviousPage()
            })
    }

    private val splashDialog: SplashDialog by lazy {
        SplashDialog(context!!)
    }

    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefresh()

        splashDialog.initSplash()
        splashDialog.showSplashScreen()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
        initUI()
    }

    private fun loadData() {
        viewModel.getLiveDataListNewChapter.observeResource(viewLifecycleOwner,
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
    }

    private fun populate(listNewChapterDomain: List<NewChapterDomain>) {
        adapterHome.addList(listNewChapterDomain)
    }

    private fun refresh() {
        adapterHome.clearList()
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
        with(recycler_view) {
            adapter = adapterHome
            val gridLayoutManager = GridLayoutManager(context, maxNumberGridLayout(context))
            gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return when (adapterHome.getItemViewType(position)) {
                        ITEM_LIST -> 1
                        ITEM_BOTTOM -> gridLayoutManager.spanCount
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

    private fun showSuccess() {
        recycler_view.visibility = View.VISIBLE
        include_layout_loading.visibility = View.GONE
        include_layout_error.visibility = View.GONE

        splashDialog.hideSplashScreen()
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

    private fun errorBottomScroll(showError: Boolean) {
        adapterHome.showErrorRetry(showError)
    }

    private fun showErrorFullScreen() {
        include_layout_error.visibility = View.VISIBLE
        include_layout_loading.visibility = View.GONE
        recycler_view.visibility = View.GONE
        viewModel.refreshViewModel()

        splashDialog.hideSplashScreen()
    }
}