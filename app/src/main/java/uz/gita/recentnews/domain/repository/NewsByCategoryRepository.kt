package uz.gita.recentnews.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.recentnews.data.source.local.room.entity.NewsEntity


interface NewsByCategoryRepository {

    fun getNewsByCategoryFromNet(category: String): Flow<Result<List<NewsEntity>>>
    fun getNewsByCategoryFromRoom(category: String): List<NewsEntity>

}