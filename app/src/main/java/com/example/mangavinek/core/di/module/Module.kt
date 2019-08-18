package com.example.mangavinek.core.di.module

import com.example.mangavinek.catalog.model.repository.CatalogRepository
import com.example.mangavinek.catalog.presentation.viewmodel.CatalogViewModel
import com.example.mangavinek.core.api.ApiServiceSoup
import com.example.mangavinek.detail.model.repository.DetailRepository
import com.example.mangavinek.detail.presentation.viewmodel.DetailViewModel
import com.example.mangavinek.home.model.repository.HomeRepository
import com.example.mangavinek.home.presentation.viewmodel.HomeViewModel
import com.example.mangavinek.publishing.model.repository.PublishingRepository
import com.example.mangavinek.publishing.presentation.viewmodel.PublishingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single<HomeRepository> { HomeRepository(get()) }
    factory<DetailRepository> { DetailRepository(get()) }
    single<PublishingRepository> { PublishingRepository() }
    factory<CatalogRepository>{ CatalogRepository(get()) }
}

val viewModelModule = module {
    viewModel<HomeViewModel> { HomeViewModel(get()) }
    viewModel<DetailViewModel> { DetailViewModel(get()) }
    viewModel<PublishingViewModel> { PublishingViewModel(get()) }
    viewModel<CatalogViewModel> { CatalogViewModel(get()) }
}

val apiServiceClientModule = module {
    factory<ApiServiceSoup> { ApiServiceSoup }
}