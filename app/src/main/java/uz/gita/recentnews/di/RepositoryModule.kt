package uz.gita.recentnews.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.recentnews.domain.repository.NewsRepository
import uz.gita.recentnews.domain.repository.impl.NewsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @[Binds Singleton]
    fun bindAuthRepository(impl: NewsRepositoryImpl): NewsRepository
}

