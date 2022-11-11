package uz.gita.recentnews_slp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.recentnews_slp.domain.repository.NewsByCategoryRepository
import uz.gita.recentnews_slp.domain.repository.NewsRepository
import uz.gita.recentnews_slp.domain.repository.impl.NewsByCategoryRepositoryImpl
import uz.gita.recentnews_slp.domain.repository.impl.NewsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun bindNewsRepository(impl: NewsRepositoryImpl): NewsRepository

    @[Binds Singleton]
    fun bindNewsByCategory (impl : NewsByCategoryRepositoryImpl) :NewsByCategoryRepository
}