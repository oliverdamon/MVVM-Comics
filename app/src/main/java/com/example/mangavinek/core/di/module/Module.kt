package com.example.mangavinek.core.di.module

import com.example.mangavinek.detail.model.repository.DetailRepository
import com.example.mangavinek.home.model.repository.HotRepositoryManga
import com.example.mangavinek.detail.presentation.viewmodel.DetailViewModel
import com.example.mangavinek.home.presentation.viewmodel.NewsViewModel
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