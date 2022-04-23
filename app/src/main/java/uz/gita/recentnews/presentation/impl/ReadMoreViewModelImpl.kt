package uz.gita.recentnews.presentation.impl

import androidx.lifecycle.MutableLiveData
import uz.gita.recentnews.presentation.ReadMoreViewModel

class ReadMoreViewModelImpl : ReadMoreViewModel {

    override val errorLivedata = MutableLiveData<String>()
    override val progressLivedata = MutableLiveData<Boolean>()
    override val favouriteScreenLiveData = MutableLiveData<Boolean>()

}