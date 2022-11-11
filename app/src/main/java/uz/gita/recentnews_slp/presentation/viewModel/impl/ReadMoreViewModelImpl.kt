package uz.gita.recentnews_slp.presentation.viewModel.impl

import androidx.lifecycle.MutableLiveData
import uz.gita.recentnews_slp.presentation.viewModel.ReadMoreViewModel

class ReadMoreViewModelImpl : ReadMoreViewModel {

    override val errorLivedata = MutableLiveData<String>()
    override val progressLivedata = MutableLiveData<Boolean>()
    override val favouriteScreenLiveData = MutableLiveData<Boolean>()

}