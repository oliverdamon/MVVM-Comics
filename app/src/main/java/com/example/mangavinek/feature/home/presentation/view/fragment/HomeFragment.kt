package com.example.mangavinek.feature.home.presentation.view.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.core.constant.BASE_URL
import com.example.mangavinek.core.helper.PaginationScroll
import com.example.mangavinek.core.helper.SplashDialog
import com.example.mangavinek.core.helper.observeResource
import com.example.mangavinek.core.util.maxNumberGridLayout
import com.example.mangavinek.feature.detail.presentation.view.activity.DetailActivity
import com.example.mangavinek.feature.home.model.domain.NewChapterDomain
import com.example.mangavinek.feature.home.presentation.view.adapter.HomeAdapter
import com.example.mangavinek.feature.home.presentation.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.layout_screen_error.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), AnkoLogger {

    companion object {
        const val TAG = "HomeFragment"
    }

    private val adapterHome: HomeAdapter by lazy {
        HomeAdapter(listNewChapterDomain) {
            if (viewModel.releasedLoad) {
                context!!.startActivity<DetailActivity>("url" to BASE_URL.plus(it.url))
            }
        }
    }

    private val splashDialog: SplashDialog by lazy {
        SplashDialog(context!!)
    }

    private val viewModel by viewModel<HomeViewModel>()

    private var listNewChapterDomain = arrayListOf<NewChapterDomain>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipe_refresh.setOnRefreshListener {
            GlobalScope.launch(context = Dispatchers.Main) {
                if (viewModel.releasedLoad) {
                    delay(1000)
                    include_layout_error.visibility = View.GONE
                    adapterHome.clear(listNewChapterDomain)
                    viewModel.refreshViewModel()
                }
                swipe_refresh.isRefreshing = false
            }
        }

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
        this.listNewChapterDomain.addAll(listNewChapterDomain)
        adapterHome.notifyItemChanged(this.listNewChapterDomain.size - 20, this.listNewChapterDomain.size)
    }

    private fun initUI() {
        with(recycler_view) {
            adapter = adapterHome
            val gridLayoutManager = GridLayoutManager(context, maxNumberGridLayout(context))
            addOnScrollListener(object : PaginationScroll(gridLayoutManager) {
                override fun loadMoreItems() {
                    viewModel.nextPage()
                    progress_bottom.visibility = View.VISIBLE
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

    private fun showSuccess() {
        recycler_view.visibility = View.VISIBLE
        progress_bottom.visibility = View.GONE
        include_layout_loading.visibility = View.GONE
        include_layout_error.visibility = View.GONE
        viewModel.releasedLoad = true
        splashDialog.hideSplashScreen()
    }

    private fun showLoading() {
        include_layout_loading.visibility = View.VISIBLE
    }

    private fun showError() {
        include_layout_error.visibility = View.VISIBLE
        include_layout_loading.visibility = View.GONE
        progress_bottom.visibility = View.GONE
        recycler_view.visibility = View.GONE
        splashDialog.hideSplashScreen()

        val currentPage = viewModel.currentPage

        image_refresh_default.setOnClickListener {
            ObjectAnimator.ofFloat(image_refresh_default, View.ROTATION, 0f, 360f).setDuration(300).start()

            if (currentPage > 1){
                viewModel.backPreviousPage()
            } else {
                adapterHome.clear(listNewChapterDomain)
                viewModel.refreshViewModel()
            }
        }

    }
}