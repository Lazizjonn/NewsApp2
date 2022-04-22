package uz.gita.recentnews.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.recentnews.data.source.local.room.dao.NewsDao
import uz.gita.recentnews.data.source.local.room.entity.NewsEntity


@Database(entities = [NewsEntity::class], version = 1)
abstract class MyRoomDataBase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
}