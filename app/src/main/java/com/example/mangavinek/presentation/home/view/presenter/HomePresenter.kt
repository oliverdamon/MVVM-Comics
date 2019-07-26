package com.example.mangavinek.presentation.home.view.presenter

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.core.util.Resource.*
import com.example.mangavinek.presentation.home.view.PresentationHome
import com.example.mangavinek.presentation.home.view.PresentationHome.Presenter
import org.jsoup.select.Elements

class HomePresenter(private val view: PresentationHome.View, private val activity: FragmentActivity) : Presenter {

    override fun initViewModel(page: Int) {
        view.viewModel().init(page)
    }

    override fun loadData() {
        view.viewModel().mutableLiveDataHot?.observe(activity, Observer<Resource<Elements>> { model ->
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