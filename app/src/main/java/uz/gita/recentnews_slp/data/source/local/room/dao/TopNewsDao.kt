package uz.gita.recentnews_slp.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import uz.gita.recentnews_slp.data.source.local.room.entity.NewsEntity
import uz.gita.recentnews_slp.data.source.local.room.entity.TopNewsEntity

@Dao
interface TopNewsDao : BaseDao<TopNewsEntity> {

    @Query("SELECT * FROM top_news_entity ")
    fun getAllNews(): List<TopNewsEntity>

    @Query("DELETE FROM top_news_entity")
    fun deleteAllTopNews()
}