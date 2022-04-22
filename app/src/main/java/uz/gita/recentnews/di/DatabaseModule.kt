package uz.gita.recentnews.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.recentnews.data.source.local.room.MyRoomDataBase


@[Module InstallIn(SingletonComponent::class)]
class DatabaseModule {

    @Provides
    fun getDatabase(@ApplicationContext context: Context): MyRoomDataBase =
        Room.databaseBuilder(context, MyRoomDataBase::class.java, "MyRoomDatabase")
            .allowMainThreadQueries()
            .build()
}