package com.example.mangavinek.presentation.detail

import com.example.mangavinek.core.base.Base
import com.example.mangavinek.model.detail.entity.DetailResponse
import com.example.mangavinek.presentation.detail.view.viewmodel.DetailViewModel
import org.jsoup.select.Elements

interface PresentationDetail {
    interface Presenter {
        fun loadData()
        fun initViewModel(url: String)
    }

    interface View : Base<Presenter, DetailViewModel>{
        fun populateDetail(detailResponse: DetailResponse?)
        fun screenSuccess()
        fun screenLoading()
        fun screenError()
    }

    interface ViewChapter : Base<Presenter, DetailViewModel>{
        fun populate(elements: Elements)
        fun screenSuccess()
        fun screenLoading()
        fun screenError()
    }
}