package uz.gita.recentnews.presentation

import androidx.lifecycle.LiveData
import uz.gita.recentnews.data.source.local.room.entity.NewsEntity

interface CategoryViewModel {

    val errorLivedata: LiveData<String>
    val readMoreLivedata: LiveData<NewsEntity>
    val progressLivedata: LiveData<Boolean>
    val newsByCategoryLivedata: LiveData<List<NewsEntity>>

    fun allNewsByCategory(query: String)
    fun readingMore(data: NewsEntity)

}