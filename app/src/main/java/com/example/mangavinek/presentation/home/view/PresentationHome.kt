package com.example.mangavinek.presentation.home.view

import com.example.mangavinek.core.base.Base
import com.example.mangavinek.presentation.home.view.viewmodel.NewsViewModel
import org.jsoup.select.Elements

interface PresentationHome {
    interface Presenter {
        fun loadData()
        fun initViewModel(page: Int = 1)
    }

    interface View : Base<Presenter, NewsViewModel>{
        fun populate(elements: Elements)
        fun screenSuccess()
        fun screenLoading()
        fun screenError()
    }
}