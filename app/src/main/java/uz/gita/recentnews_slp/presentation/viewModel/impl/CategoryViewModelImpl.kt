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
import uz.gita.recentnews_slp.domain.repository.NewsByCategoryRepository
import uz.gita.recentnews_slp.presentation.viewModel.CategoryViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModelImpl @Inject constructor(
    private val repository: NewsByCategoryRepository
) : CategoryViewModel, ViewModel() {

    override val errorLivedata = MutableLiveData<String>()
    override val readMoreLivedata = MutableLiveData<NewsEntity>()
    override val progressLivedata = MutableLiveData<Boolean>()
    override val newsByCategoryLivedata: MutableLiveData<List<NewsEntity>> = MutableLiveData()


    override fun allNewsByCategory(query: String) {

        if (isConnected()) {
            progressLivedata.value = true
            val response = repository.getNewsByCategoryFromNet(query).onEach {

                it.onSuccess {
                    newsByCategoryLivedata.value = it
                    progressLivedata.value = false
                }

                it.onFailure {
                    errorLivedata.value = when (it) {
                        is IOException -> {
                            "No Internet connection"
                        }
                        is HttpException -> {
                            it.message()
                        }
                        else -> it.toString()
                    }
                    progressLivedata.value = false
                }
            }.launchIn(viewModelScope)


        } else {
            progressLivedata.value = true
            newsByCategoryLivedata.value = repository.getNewsByCategoryFromRoom(query)
            progressLivedata.value = false
        }
    }

    override fun readingMore(data: NewsEntity) {
        readMoreLivedata.value = data
    }
}