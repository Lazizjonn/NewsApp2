package uz.gita.recentnews.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.recentnews.data.source.local.room.entity.NewsEntity


interface NewsRepository {

    fun getAllNewsFromNet(query: String): Flow<Result<List<NewsEntity>>>
    fun getAllNewsFromRoom(query: String): List<NewsEntity>
    fun addToFavourites(newsFav: NewsEntity)
}