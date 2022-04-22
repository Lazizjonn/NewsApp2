package uz.gita.recentnews.data.source.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favourite_entity")
data class FavouriteEntity(
    @PrimaryKey
    val title: String,
    val image: String,
    @SerializedName("read_more")
    val readMore: String,
    val author: String,
    val description: String,
    @SerializedName("inshorts_link")
    val inshortsLink: String,
    val timestamp: String,
    @SerializedName("category_name")
    val categoryName: String
)