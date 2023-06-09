package uz.gita.recentnews_slp.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "news_entity")
data class NewsEntity(

    val image: String,
    val readMore: String?,
    val author: String,
    val description: String,
    val inshortsLink: String,
    @PrimaryKey
    val title: String,
    val timestamp: String,
    val category: String,
    ) : Serializable {

       fun toTopNews () = TopNewsEntity(image, readMore, author, description, inshortsLink, title, timestamp, category)
    }