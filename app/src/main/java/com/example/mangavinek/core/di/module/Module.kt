package com.example.mangavinek.core.di.module

import com.example.mangavinek.model.detail.repository.DetailRepository
import com.example.mangavinek.model.home.repository.HotRepositoryManga
import com.example.mangavinek.presentation.detail.view.viewmodel.DetailViewModel
import com.example.mangavinek.presentation.home.view.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModule = module {

    single<HotRepositoryManga> {
        HotRepositoryManga()
    }

    viewModel<NewsViewModel> {
        NewsViewModel(get())
    }

    factory <DetailRepository> {
        DetailRepository()
    }

    viewModel<DetailViewModel> {
        DetailViewModel(get())
    }
}