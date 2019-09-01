package com.example.mangavinek.core.di.module

import com.example.mangavinek.feature.catalog.repository.CatalogRepository
import com.example.mangavinek.feature.catalog.presentation.viewmodel.CatalogViewModel
import com.example.mangavinek.data.source.local.AppDatabase
import com.example.mangavinek.data.source.local.FavoriteDao
import com.example.mangavinek.data.source.remote.ApiServiceSoup
import com.example.mangavinek.feature.detail.repository.DetailRepository
import com.example.mangavinek.feature.detail.presentation.viewmodel.DetailViewModel
import com.example.mangavinek.feature.favorite.presentation.viewmodel.FavoriteViewModel
import com.example.mangavinek.feature.favorite.repository.FavoriteRepository
import com.example.mangavinek.feature.home.repository.HomeRepository
import com.example.mangavinek.feature.home.presentation.viewmodel.HomeViewModel
import com.example.mangavinek.feature.publishing.repository.PublishingRepository
import com.example.mangavinek.feature.publishing.presentation.viewmodel.PublishingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single<HomeRepository> { HomeRepository(get()) }
    factory<DetailRepository> { DetailRepository(get(), get()) }
    single<PublishingRepository> { PublishingRepository() }
    factory<CatalogRepository> { CatalogRepository(get()) }
    single<FavoriteRepository> { FavoriteRepository(get()) }
}

val viewModelModule = module {
    viewModel<HomeViewModel> { HomeViewModel(get()) }
    viewModel<DetailViewModel> { DetailViewModel(get()) }
    viewModel<PublishingViewModel> { PublishingViewModel(get()) }
    viewModel<CatalogViewModel> { CatalogViewModel(get()) }
    viewModel<FavoriteViewModel> { FavoriteViewModel(get()) }
}

val apiServiceClientModule = module {
    factory<ApiServiceSoup> { ApiServiceSoup }
}

val databaseModule = module {
    single<AppDatabase> { AppDatabase.getAppDataBase(context = get()) }
    single<FavoriteDao> { get<AppDatabase>().favoriteDao() }
}