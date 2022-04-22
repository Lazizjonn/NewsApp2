package uz.gita.recentnews.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import uz.gita.recentnews.data.source.local.room.entity.FavouriteEntity
import uz.gita.recentnews.data.source.local.room.entity.NewsEntity

@Dao
interface FavouriteDao : BaseDao<FavouriteEntity> {

    @Query("SELECT * FROM favourite_entity")
    fun getAllFavourites () : List<FavouriteEntity>

}