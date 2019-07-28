package com.example.mangavinek.presentation.detail.presenter

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.core.util.Resource.*
import com.example.mangavinek.model.detail.entity.DetailResponse
import com.example.mangavinek.presentation.detail.PresentationDetail
import org.jsoup.select.Elements

class DetailPresenter(private val view: PresentationDetail.View) : PresentationDetail.Presenter {

    private val viewModel = view.viewModel()
    private val activity = view as FragmentActivity

    override fun initViewModel(url: String) {
        viewModel.init(url)
    }

    override fun loadData() {
        viewModel.mutableLiveData?.observe(activity, Observer<Resource<Elements>> { model ->
            when (model) {
                is Start -> {
                }
                is Success -> {
                    view.populateDetail(DetailResponse(model.data[0]))
                    view.screenSuccess()
                }
                is Error -> {
                }
            }
        })
    }
}