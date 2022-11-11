package uz.gita.recentnews_slp.presentation.viewModel

import androidx.lifecycle.LiveData

interface ReadMoreViewModel {

    val errorLivedata: LiveData<String>
    val progressLivedata: LiveData<Boolean>
    val favouriteScreenLiveData: LiveData<Boolean>

}