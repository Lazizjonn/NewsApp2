package uz.gita.recentnews_slp.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.recentnews_slp.data.source.local.room.dao.NewsDao
import uz.gita.recentnews_slp.data.source.local.room.dao.TopNewsDao
import uz.gita.recentnews_slp.data.source.local.room.entity.NewsEntity
import uz.gita.recentnews_slp.data.source.local.room.entity.TopNewsEntity


@Database(entities = [NewsEntity::class, TopNewsEntity::class], exportSchema = false, version = 1)
abstract class MyRoomDataBase : RoomDatabase() {
    abstract fun getNewsDao(): NewsDao
    abstract fun getTopNewsDao() : TopNewsDao
}