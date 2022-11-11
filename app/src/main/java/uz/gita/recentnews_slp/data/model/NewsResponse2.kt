package uz.gita.recentnews_slp.data.model

import com.google.gson.annotations.SerializedName
import uz.gita.recentnews_slp.data.source.local.room.entity.NewsEntity
import uz.gita.recentnews_slp.data.source.local.room.entity.TopNewsEntity


sealed class NewResponse2 {

    data class Response2(

        @field:SerializedName("data")
        val data: Data,

        @field:SerializedName("message")
        val message: Any,

        @field:SerializedName("status")
        val status: String
    )

    data class Data(

        @field:SerializedName("count")
        val count: Int,

        @field:SerializedName("articles")
        val articles: List<ArticlesItem>
    )

    data class ArticlesItem(

        @field:SerializedName("relevancyTags")
        val relevancyTags: List<Any>,

        @field:SerializedName("language")
        val language: String,

        @field:SerializedName("title")
        val title: String,

        @field:SerializedName("hashId")
        val hashId: String,

        @field:SerializedName("content")
        val content: String,

        @field:SerializedName("sourceUrl")
        val sourceUrl: String,

        @field:SerializedName("categoryNames")
        val categoryNames: List<String>,

        @field:SerializedName("important")
        val important: Boolean,

        @field:SerializedName("score")
        val score: Int,

        @field:SerializedName("createdAt")
        val createdAt: Long,

        @field:SerializedName("oldHashId")
        val oldHashId: String,

        @field:SerializedName("shortenedUrl")
        val shortenedUrl: String,

        @field:SerializedName("authorName")
        val authorName: String,

        @field:SerializedName("countryCode")
        val countryCode: String,

        @field:SerializedName("subtitle")
        val subtitle: String,

        @field:SerializedName("imageUrl")
        val imageUrl: String,

        @field:SerializedName("sourceName")
        val sourceName: String
    )
}

fun NewResponse2.ArticlesItem.toArticlesEntity(category: String): NewsEntity =
    NewsEntity(
        image = imageUrl, readMore = sourceUrl, author = authorName, description = content,
        inshortsLink = shortenedUrl, title = title, timestamp = createdAt.toString(), category = category
    )
fun NewResponse2.ArticlesItem.toTopNewsEntity(category: String): TopNewsEntity =
    TopNewsEntity(
        image = imageUrl, readMore = sourceUrl, author = authorName, description = content,
        inshortsLink = shortenedUrl, title = title, timestamp = createdAt.toString(), category = category
    )