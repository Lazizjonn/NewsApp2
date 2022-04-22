package uz.gita.recentnews.presentation.impl

import androidx.lifecycle.MutableLiveData
import uz.gita.recentnews.presentation.ReadMoreViewModel

class ReadMoreViewModelImpl : ReadMoreViewModel {
    override val errorLivedata = MutableLiveData<String>()
    override val progressLivedata = MutableLiveData<Boolean>()
    override val favouriteScreenLiveData = MutableLiveData<Boolean>()
    override val isFavouriteLiveData = MutableLiveData<Boolean>()

    override fun changeFavourite(isFav: Boolean) {
        TODO("Not yet implemented")
    }

    override fun gotoFavouriteScreen() {
        TODO("Not yet implemented")
    }

}