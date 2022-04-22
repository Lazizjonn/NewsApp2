package uz.gita.recentnews.data.source.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import uz.gita.recentnews.data.model.responce.NewsResponse

interface Api {

    @GET("news")
    suspend fun getAllNews(@Query("category") query: String): Response<NewsResponse.Response>

}
