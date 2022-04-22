package uz.gita.recentnews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.gita.recentnews.BuildConfig.BASE_URL
import uz.gita.recentnews.data.source.remote.Api
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    fun logging(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    var client = OkHttpClient.Builder().addInterceptor(logging()).build()

    @Singleton
    @Provides
    fun getRetrofit(): Retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).client(client).build()

    @[Singleton Provides]
    fun getApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)
}
