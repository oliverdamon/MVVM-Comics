package com.example.mangavinek.presentation.home.view

import com.example.mangavinek.presentation.home.view.viewmodel.NewsViewModel
import org.jsoup.select.Elements

interface PresentationHome {
    interface Presenter {
        fun loadData()
        fun initViewModel(page: Int = 1)
    }

    interface View{
        fun populate(elements: Elements)
        fun viewModel(): NewsViewModel
        fun screenSuccess()
        fun screenLoading()
        fun screenError()
    }
}