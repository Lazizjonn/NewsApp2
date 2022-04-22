package uz.gita.recentnews.presentation

import androidx.lifecycle.LiveData
import uz.gita.recentnews.data.source.local.room.entity.NewsEntity

interface MainViewModel {

    val errorLivedata: LiveData<String>
    val progressLivedata: LiveData<Boolean>
    val loadNewsLivedata: LiveData<List<NewsEntity>>
    val openFavouriteScreenLiveData: LiveData<Unit>


    fun allNews(query: String)
    fun openFavouriteScreen()

}