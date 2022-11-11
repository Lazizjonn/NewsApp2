package uz.gita.recentnews_slp.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import uz.gita.recentnews_slp.data.source.local.room.entity.NewsEntity


@Dao
interface NewsDao : BaseDao<NewsEntity> {

    @Query("SELECT * FROM news_entity WHERE category = :category ")
    fun getAllNews(category: String): List<NewsEntity>

    @Query("DELETE FROM news_entity WHERE category = :category")
    fun deleteAllByCategory(category: String)

}