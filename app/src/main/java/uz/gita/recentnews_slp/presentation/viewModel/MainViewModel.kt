package uz.gita.recentnews_slp.presentation.viewModel

import androidx.lifecycle.LiveData
import uz.gita.recentnews_slp.data.source.local.room.entity.NewsEntity
import uz.gita.recentnews_slp.data.source.local.room.entity.TopNewsEntity

interface MainViewModel {

    val errorLivedata: LiveData<String>
    val progressLivedata: LiveData<Boolean>
    val loadNewsLivedata: LiveData<List<TopNewsEntity>>
    val readMoreLivedata: LiveData<NewsEntity>
    val moveToCategoryLivedata: LiveData<Unit>

    fun moveToCategory(category: String)
    fun allNews(query: String)
    fun readMore(data: NewsEntity)
    fun topNews()
}