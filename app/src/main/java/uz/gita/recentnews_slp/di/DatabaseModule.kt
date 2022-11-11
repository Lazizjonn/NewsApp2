package uz.gita.recentnews_slp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.recentnews_slp.data.source.local.room.MyRoomDataBase
import uz.gita.recentnews_slp.data.source.local.room.dao.NewsDao
import uz.gita.recentnews_slp.data.source.local.room.dao.TopNewsDao


@[Module InstallIn(SingletonComponent::class)]
class DatabaseModule {

    @Provides
    fun getDatabase(@ApplicationContext context: Context): MyRoomDataBase =
        Room.databaseBuilder(context, MyRoomDataBase::class.java, "MyRoomDatabase")
            .allowMainThreadQueries()
            .build()

    @Provides
    fun getNewsDao(@ApplicationContext context: Context): NewsDao =
        getDatabase(context).getNewsDao()

    @Provides
    fun getTopNewsDao(@ApplicationContext context: Context): TopNewsDao =
        getDatabase(context).getTopNewsDao()
}