package com.example.mangavinek.presentation.home.presenter

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.core.util.Resource.*
import com.example.mangavinek.presentation.home.PresentationHome
import com.example.mangavinek.presentation.home.PresentationHome.Presenter
import org.jsoup.select.Elements

class HomePresenter(private val view: PresentationHome.View, private val activity: FragmentActivity) : Presenter {

    private val viewModel = view.viewModel()

    override fun initViewModel(page: Int) {
        viewModel.init(page)
    }

    override fun loadData() {
        viewModel.mutableLiveDataHot?.observe(activity, Observer<Resource<Elements>> { model ->
            when (model) {
                is Start -> {
                    view.screenLoading()
                }
                is Success -> {
                    view.populate(model.data)
                    view.screenSuccess()
                }
                is Error -> {
                    view.screenError()
                }
            }
        })
    }
}