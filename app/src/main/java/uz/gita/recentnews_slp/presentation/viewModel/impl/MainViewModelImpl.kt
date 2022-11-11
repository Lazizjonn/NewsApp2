package uz.gita.recentnews_slp.presentation.viewModel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okio.IOException
import retrofit2.HttpException
import uz.gita.recentnews_slp.utils.isConnected
import uz.gita.recentnews_slp.data.source.local.room.entity.NewsEntity
import uz.gita.recentnews_slp.data.source.local.room.entity.TopNewsEntity
import uz.gita.recentnews_slp.domain.repository.NewsRepository
import uz.gita.recentnews_slp.presentation.viewModel.MainViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(
    private val repository: NewsRepository,
) : ViewModel(), MainViewModel {

    override var errorLivedata = MutableLiveData<String>()
    override var progressLivedata = MutableLiveData<Boolean>()
    override var loadNewsLivedata = MutableLiveData<List<TopNewsEntity>>()
    override val readMoreLivedata = MutableLiveData<NewsEntity>()
    override val moveToCategoryLivedata = MutableLiveData<Unit>()

    init {
        topNews()
    }

    override fun allNews(query: String) {
        progressLivedata.value = true

        if (isConnected()) {
            repository.getAllNewsFromNet(query)
                .onEach { progressLivedata.value = false }
                .onEach { it.onSuccess { loadNewsLivedata.value = it.map { it.toTopNews() } } }
                .onEach {
                    it.onFailure {
                        errorLivedata.value = when (it) {
                            is IOException -> "No internet connection"
                            is HttpException -> it.response()?.message()
                            else -> it.message
                        }
                    }
                }.launchIn(viewModelScope)

        } else {
            loadNewsLivedata.value = repository.getAllNewsFromRoom(query).map { it.toTopNews() }
            progressLivedata.value = false
        }
    }

    override fun readMore(data: NewsEntity) {
        readMoreLivedata.value = data
    }

    override fun topNews() {
        progressLivedata.value = true

        if (isConnected()) {
            repository.getTopNewsFromNet()
                .onEach { progressLivedata.value = false }
                .onEach { it.onSuccess { loadNewsLivedata.value = it } }
                .onEach {
                    it.onFailure {
                        errorLivedata.value = when (it) {
                            is IOException -> "No internet connection "
                            is HttpException -> it.response()?.message()
                            else -> it.message
                        }
                    }
                }.launchIn(viewModelScope)
        } else {
            progressLivedata.value = false
            loadNewsLivedata.value = repository.getTopNewsFromRoom()
        }
    }

    override fun moveToCategory(category: String) {
        moveToCategoryLivedata.value = Unit
    }


}