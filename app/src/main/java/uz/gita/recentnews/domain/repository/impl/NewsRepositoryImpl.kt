package uz.gita.recentnews.domain.repository.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.recentnews.data.model.toArticlesEntity
import uz.gita.recentnews.data.source.local.room.MyRoomDataBase
import uz.gita.recentnews.data.source.local.room.entity.NewsEntity
import uz.gita.recentnews.data.source.remote.Api
import uz.gita.recentnews.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: Api,
    private val room: MyRoomDataBase
) : NewsRepository {

    override fun getAllNewsFromNet(query: String): Flow<Result<List<NewsEntity>>> = flow<Result<List<NewsEntity>>> {
        val response = api.getAllNews(query)
        if (response.isSuccessful) {
            val list: ArrayList<NewsEntity> = ArrayList()
            response.body()?.let { list.addAll(it.articles.map { it.toArticlesEntity(query) }) }
            room.getNewsDao().deleteAllByCategory(query)
            room.getNewsDao().insertAll(list)
            emit(Result.success(room.getNewsDao().getAllNews(query)))
        } else {
            emit(Result.failure(Exception("Response is unsuccessful")))
        }

    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)

    override fun getAllNewsFromRoom(query: String): List<NewsEntity> = room.getNewsDao().getAllNews(query)

}