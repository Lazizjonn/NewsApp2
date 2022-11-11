package uz.gita.recentnews_slp.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "top_news_entity")
data class TopNewsEntity(
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
    fun toNewsEntity () = NewsEntity(image, readMore, author, description, inshortsLink, title, timestamp, category)
}

