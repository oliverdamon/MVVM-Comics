package com.example.mangavinek.feature.favorite.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.core.util.maxNumberGridLayout
import com.example.mangavinek.feature.model.favorite.entity.FavoriteDB
import com.example.mangavinek.feature.detail.presentation.ui.activity.DetailActivity
import com.example.mangavinek.feature.favorite.presentation.ui.adapter.FavoriteAdapter
import com.example.mangavinek.feature.favorite.presentation.viewmodel.FavoriteViewModel
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

    private var itemList = arrayListOf<FavoriteDB>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadData()
        initUi()
    }

    private fun loadData() {
        viewModel.getLiveDataListFavoriteDB?.observe(this, Observer {
            adapterFavorite.atualiza(it)
            showNotEmpty()
        })
    }

    private fun initUi() {
        with(recycler_favorite) {
            adapter = adapterFavorite
            val gridLayoutManager = GridLayoutManager(context, maxNumberGridLayout(context))
            layoutManager = gridLayoutManager
        }
    }

    private fun showNotEmpty() {
        text_favorite_empty.visibility = if (itemList.isNotEmpty()) View.GONE else View.VISIBLE
    }
}