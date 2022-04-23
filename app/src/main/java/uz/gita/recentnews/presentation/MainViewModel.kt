package uz.gita.recentnews.presentation

import androidx.lifecycle.LiveData
import uz.gita.recentnews.data.source.local.room.entity.NewsEntity

interface MainViewModel {

    val errorLivedata: LiveData<String>
    val progressLivedata: LiveData<Boolean>
    val loadNewsLivedata: LiveData<List<NewsEntity>>
    val readMoreLivedata: LiveData<NewsEntity>
    val moveToCategoryLivedata: LiveData<Unit>

    fun moveToCategory(category: String)
    fun allNews(query: String)
    fun readMore(data: NewsEntity)
}