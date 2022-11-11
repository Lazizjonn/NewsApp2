package uz.gita.recentnews_slp.presentation.viewModel

import androidx.lifecycle.LiveData
import uz.gita.recentnews_slp.data.source.local.room.entity.NewsEntity

interface CategoryViewModel {

    val errorLivedata: LiveData<String>
    val readMoreLivedata: LiveData<NewsEntity>
    val progressLivedata: LiveData<Boolean>
    val newsByCategoryLivedata: LiveData<List<NewsEntity>>

    fun allNewsByCategory(query: String)
    fun readingMore(data: NewsEntity)

}