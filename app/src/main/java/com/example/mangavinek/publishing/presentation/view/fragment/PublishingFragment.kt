package com.example.mangavinek.publishing.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangavinek.R
import com.example.mangavinek.catalog.presentation.view.activity.CatalogActivity
import com.example.mangavinek.core.constant.BASE_URL_PUBLISHING
import com.example.mangavinek.core.constant.BASE_URL_PUBLISHING_DEFAULT
import com.example.mangavinek.publishing.model.domain.mock.PublishingObject
import com.example.mangavinek.publishing.presentation.view.adapter.PublishingAdapter
import com.example.mangavinek.publishing.presentation.viewmodel.PublishingViewModel
import kotlinx.android.synthetic.main.fragment_publishing.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class PublishingFragment: Fragment(){
    companion object {
        const val TAG = "PublishingFragment"
    }

    private val adapterItem: PublishingAdapter by lazy {
        PublishingAdapter(itemList) {
            val url = if (it.name == "Todas") BASE_URL_PUBLISHING_DEFAULT
            else String.format(BASE_URL_PUBLISHING, it.name)

            context!!.startActivity<CatalogActivity>("url" to url)
        }
    }

    private val viewModel by viewModel<PublishingViewModel>()

    private var itemList = arrayListOf<PublishingObject>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_publishing, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadData()
        initUi()
    }

    private fun loadData() {
        viewModel.fetchList()
        viewModel.getListPublishing.observe(this, Observer {
            itemList.addAll(it)
            adapterItem.notifyDataSetChanged()
        })
    }

    private fun initUi() {
        with(recycler_publishing) {
            adapter = adapterItem
            isNestedScrollingEnabled = false
            isFocusable = false
            val gridLayoutManager = GridLayoutManager(context, 2)
            layoutManager = gridLayoutManager
        }
    }
}