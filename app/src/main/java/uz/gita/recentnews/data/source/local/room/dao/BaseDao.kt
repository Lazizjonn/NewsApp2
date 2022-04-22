package uz.gita.recentnews.data.source.local.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(t: List<T>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: T)

    @Delete
    fun delete(t: T)

}