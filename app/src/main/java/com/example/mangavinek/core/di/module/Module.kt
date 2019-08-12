package com.example.mangavinek.core.di.module

import com.example.mangavinek.core.api.ApiServiceSoup
import com.example.mangavinek.detail.model.repository.DetailRepository
import com.example.mangavinek.detail.presentation.viewmodel.DetailViewModel
import com.example.mangavinek.home.model.repository.HotRepositoryManga
import com.example.mangavinek.home.presentation.viewmodel.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single<HotRepositoryManga> { HotRepositoryManga(get()) }
    factory<DetailRepository> { DetailRepository(get()) }
}

val viewModelModule = module {
    viewModel<NewsViewModel> { NewsViewModel(get()) }
    viewModel<DetailViewModel> { DetailViewModel(get()) }
}

val apiServiceClientModule = module {
    factory<ApiServiceSoup> { ApiServiceSoup }
}