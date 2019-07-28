package com.example.mangavinek.presentation.detail.presenter

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import com.example.mangavinek.core.util.Resource
import com.example.mangavinek.core.util.Resource.*
import com.example.mangavinek.presentation.detail.PresentationDetail
import org.jsoup.select.Elements

class DetailChapterPresenter(private val view: PresentationDetail.ViewChapter, private val activity: FragmentActivity) : PresentationDetail.Presenter {

    private val viewModel = view.viewModel()

    override fun initViewModel(url: String) {
        viewModel.initChapter(url)
    }

    override fun loadData() {
        viewModel.mutableLiveDataChapter?.observe(activity, Observer<Resource<Elements>> { model ->
            when (model) {
                is Start -> {
                    //view.screenLoading()
                }
                is Success -> {
                    view.populate(model.data)
                    view.screenSuccess()
                }
                is Error -> {
                    //view.screenError()
                }
            }
        })
    }
}