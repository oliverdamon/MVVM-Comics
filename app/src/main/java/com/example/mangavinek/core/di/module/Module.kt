package com.example.mangavinek.core.di.module

import com.example.mangavinek.core.api.ApiServiceSoup
import com.example.mangavinek.detail.model.repository.DetailRepository
import com.example.mangavinek.detail.presentation.viewmodel.DetailViewModel
import com.example.mangavinek.home.model.repository.HotRepositoryManga
import com.example.mangavinek.home.presentation.viewmodel.NewsViewModel
import com.example.mangavinek.publishing.model.repository.PublishingRepository
import com.example.mangavinek.publishing.presentation.viewmodel.PublishingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single<HotRepositoryManga> { HotRepositoryManga(get()) }
    factory<DetailRepository> { DetailRepository(get()) }
    single<PublishingRepository> { PublishingRepository() }
}

val viewModelModule = module {
    viewModel<NewsViewModel> { NewsViewModel(get()) }
    viewModel<DetailViewModel> { DetailViewModel(get()) }
    viewModel<PublishingViewModel> { PublishingViewModel(get()) }
}

val apiServiceClientModule = module {
    factory<ApiServiceSoup> { ApiServiceSoup }
}