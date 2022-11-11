package uz.gita.recentnews_slp.domain.repository.impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.recentnews_slp.data.model.toArticlesEntity
import uz.gita.recentnews_slp.data.model.toTopNewsEntity
import uz.gita.recentnews_slp.data.source.local.room.MyRoomDataBase
import uz.gita.recentnews_slp.data.source.local.room.dao.NewsDao
import uz.gita.recentnews_slp.data.source.local.room.dao.TopNewsDao
import uz.gita.recentnews_slp.data.source.local.room.entity.NewsEntity
import uz.gita.recentnews_slp.data.source.local.room.entity.TopNewsEntity
import uz.gita.recentnews_slp.data.source.remote.Api
import uz.gita.recentnews_slp.di.DatabaseModule_GetTopNewsDaoFactory.getTopNewsDao
import uz.gita.recentnews_slp.domain.repository.NewsRepository
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val api: Api,
    private val getTopNewsDao : TopNewsDao,
    private val getNewsDao : NewsDao,
) : NewsRepository {

    override fun getAllNewsFromNet(query: String): Flow<Result<List<NewsEntity>>> = flow<Result<List<NewsEntity>>> {
        val response = api.getAllNews(query)
        if (response.isSuccessful) {
            val list: ArrayList<NewsEntity> = ArrayList()
            response.body()?.let { list.addAll(it.data.articles.map { it -> it.toArticlesEntity(query) }) }
            getNewsDao.deleteAllByCategory(query)
            getNewsDao.insertAll(list)
            emit(Result.success(getNewsDao.getAllNews(query)))
        } else {
            emit(Result.failure(Exception("Response is unsuccessful")))
        }

    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)

    override fun getAllNewsFromRoom(query: String): List<NewsEntity> = getNewsDao.getAllNews(query)

    override fun getTopNewsFromNet(): Flow<Result<List<TopNewsEntity>>> = flow<Result<List<TopNewsEntity>>> {
        Log.d("TTT", "getTopNewsFromNet:  ishlayapti ")
        val response = api.getTopNews()
        if (response.isSuccessful) {
            val list : ArrayList<TopNewsEntity> = ArrayList()
            response.body()?.let { list.addAll(it.data.articles.map { it -> it.toTopNewsEntity("")}) }
            getTopNewsDao.deleteAllTopNews()
            getTopNewsDao.insertAll(list)
            Log.d("TTT", "getTopNewsFromNet:  ishlayapti 2")
            emit(Result.success(getTopNewsDao.getAllNews()))
        } else {
            emit(Result.failure(Exception("Response is unsuccessful")))
        }
    }
        .catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)

    override fun getTopNewsFromRoom(): List<TopNewsEntity> = getTopNewsDao.getAllNews()

}