package com.example.mangavinek.favorite.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.core.util.maxNumberGridLayout
import com.example.mangavinek.data.model.favorite.entity.Favorite
import com.example.mangavinek.detail.presentation.view.activity.DetailActivity
import com.example.mangavinek.favorite.presentation.view.adapter.FavoriteAdapter
import com.example.mangavinek.favorite.presentation.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.fragment_favorite.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteFragment : Fragment(), AnkoLogger {

    companion object {
        const val TAG = "FavoriteFragment"
    }

    private val adapterFavorite: FavoriteAdapter by lazy {
        FavoriteAdapter(itemList) {
            context!!.startActivity<DetailActivity>("url" to it.link)
        }
    }

    private val viewModel by viewModel<FavoriteViewModel>()

    private var itemList = arrayListOf<Favorite>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
        initUi()
    }

    private fun loadData() {
        viewModel.getComicList()
        viewModel.getList?.observe(this, Observer {
            populate(it)
        })
    }

    private fun populate(listFavorite: List<Favorite>) {
        itemList.addAll(listFavorite)
        adapterFavorite.notifyDataSetChanged()
    }

    private fun initUi() {
        with(recycler_favorite) {
            adapter = adapterFavorite
            val gridLayoutManager = GridLayoutManager(context, maxNumberGridLayout(context))
            layoutManager = gridLayoutManager
        }
    }

    private fun showSuccess() {
        recycler_favorite.visibility = View.VISIBLE
    }

    private fun showLoading(isVisible: Boolean) {
        progress_bar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun showError() {
        text_erro.visibility = View.VISIBLE
        recycler_favorite.visibility = View.GONE
    }
}