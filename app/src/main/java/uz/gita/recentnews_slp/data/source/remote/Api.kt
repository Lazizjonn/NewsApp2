package uz.gita.recentnews_slp.data.source.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import uz.gita.recentnews_slp.data.model.NewResponse2

interface Api {

    @GET("topics/{query}")
    suspend fun getAllNews(@Path("query") query: String): Response<NewResponse2.Response2>

    @GET("top?offset=0&limit=15")
    suspend fun getTopNews(): Response<NewResponse2.Response2>

}
