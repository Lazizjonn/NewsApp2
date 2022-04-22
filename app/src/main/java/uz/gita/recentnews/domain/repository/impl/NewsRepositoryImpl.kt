package uz.gita.recentnews.domain.repository.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import uz.gita.recentnews.data.model.responce.toArticlesEntity
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
            val a: ArrayList<NewsEntity> = ArrayList()

            response.body()?.let { a.addAll(it.articles.map { it.toArticlesEntity(query) }) }

//                room.getNewsDao().insertAll(it1.articles.map { it2 -> it2.toEntity(query) }
//                    NewsEntity(
//                        title = it2.title,
//                        author = it2.author,
//                        description = it2.description,
//                        image = it2.image,
//                        inshortsLink = it2.inshorts_link,
//                        readMore = it2.read_more,
//                        timestamp = it2.timestamp,
//                        categoryName = query
//                    )
//                })
//                        Log . d ("TTT", "a ${a.size}")
//
//                val b = NewsEntity(
//                    title = "      market value in its biggest drop since 2004    ",
//                    description = "Netflix shares crashed 35% on Wednesday, wiping off $54 billion from the company's market value in its biggest drop since 2004. It made Netflix the worst-performing stock of the year on both S&P 500 and Nasdaq 100 indexes. Netflix shares crashed after it reported losing 2,00,000 subscribers in Q1 2022, marking the first drop in subscribers in over 10 years.",
//                    author = "Rragya Swastik",
//                    inshortsLink = "https://inshorts.com/en/news/$54-billion-wiped-off-netflixs-market-value-in-its-biggest-drop-since-2004-1650532364230",
//                    image = "https://static.inshorts.com/inshorts/images/v1/variants/jpg/m/2022/04_apr/21_thu/img_1650530917514_824.jpg?",
//                    timestamp = "2022-04-21T09:12:44.000Z",
//                    readMore = "https://www.bloombergquint.com/business/netflix-loses-200-000-customers-its-first-decline-in-a-decade?utm_campaign=fullarticle&utm_medium=referral&utm_source=inshorts ",
//                    categoryName = "all"
//                )
//
//                a.add(b)
//                val bb = NewsEntity(
//                    title = "      $54 billion wiped off Netflix's market value in its biggest drop since 2004    ",
//                    description = "Netflix shares crashed 35% on Wednesday, wiping off $54 billion from the company's market value in its biggest drop since 2004. It made Netflix the worst-performing stock of the year on both S&P 500 and Nasdaq 100 indexes. Netflix shares crashed after it reported losing 2,00,000 subscribers in Q1 2022, marking the first drop in subscribers in over 10 years.",
//                    author = "Pragya Swastik",
//                    inshortsLink = "https://inshorts.com/en/news/$54-billion-wiped-off-netflixs-market-value-in-its-biggest-drop-since-2004-1650532364230",
//                    image = "https://static.inshorts.com/inshorts/images/v1/variants/jpg/m/2022/04_apr/21_thu/img_1650530917514_824.jpg?",
//                    timestamp = "2022-04-21T09:12:44.000Z",
//                    readMore = "https://www.bloombergquint.com/business/netflix-loses-200-000-customers-its-first-decline-in-a-decade?utm_campaign=fullarticle&utm_medium=referral&utm_source=inshorts ",
//                    categoryName = "all"
//                )
//                a.add(bb)

//            room.getNewsDao().insertAll(a)

            emit(Result.success(a))

        } else {
            emit(Result.failure(Exception("Response is unsuccessful")))
        }

    }.catch { emit(Result.failure(it)) }
        .flowOn(Dispatchers.IO)


    override fun getAllNewsFromRoom(query: String): List<NewsEntity> = room.getNewsDao().getAllNews(query)

}