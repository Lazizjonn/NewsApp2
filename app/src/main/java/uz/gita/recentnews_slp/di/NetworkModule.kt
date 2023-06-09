package uz.gita.recentnews_slp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.recentnews_slp.BuildConfig.BASE_URL
import uz.gita.recentnews_slp.data.source.remote.Api
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @[Singleton Provides]
    fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client())
        .build()

    @[Singleton Provides]
    fun getApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

    private fun logging(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private fun client() = OkHttpClient.Builder().addInterceptor(logging()).build()
}
