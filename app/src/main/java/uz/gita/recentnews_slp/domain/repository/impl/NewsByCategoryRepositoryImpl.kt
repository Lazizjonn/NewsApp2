package uz.gita.recentnews_slp.domain.repository.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.recentnews_slp.data.model.toArticlesEntity
import uz.gita.recentnews_slp.data.source.local.room.dao.NewsDao
import uz.gita.recentnews_slp.data.source.local.room.entity.NewsEntity
import uz.gita.recentnews_slp.data.source.remote.Api
import uz.gita.recentnews_slp.domain.repository.NewsByCategoryRepository
import javax.inject.Inject

class NewsByCategoryRepositoryImpl @Inject constructor(
    private val api: Api,
    private val dao: NewsDao
) : NewsByCategoryRepository {

    override fun getNewsByCategoryFromNet(category: String): Flow<Result<List<NewsEntity>>> = flow<Result<List<NewsEntity>>> {

        val response = api.getAllNews(category)

        if (response.isSuccessful) {
            response.body()?.let { data ->
                dao.deleteAllByCategory(category)
                dao.insertAll(data.data.articles.map { it.toArticlesEntity(category) })
            }
            emit(Result.success(dao.getAllNews(category)))
        } else {
            emit(Result.failure(Exception("Result unsuccessful")))
        }
    }
        .catch {
            emit(Result.failure(Exception("Error: $this")))
        }
        .flowOn(Dispatchers.IO)

    override fun getNewsByCategoryFromRoom(category: String): List<NewsEntity> = dao.getAllNews(category)

}