package uz.gita.recentnews_slp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.recentnews_slp.data.source.local.room.entity.NewsEntity
import uz.gita.recentnews_slp.data.source.local.room.entity.TopNewsEntity


interface NewsRepository {

    fun getAllNewsFromNet(query: String): Flow<Result<List<NewsEntity>>>
    fun getAllNewsFromRoom(query: String): List<NewsEntity>
    fun getTopNewsFromNet(): Flow<Result<List<TopNewsEntity>>>
    fun getTopNewsFromRoom(): List<TopNewsEntity>
}