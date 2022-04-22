package uz.gita.recentnews.presentation

import androidx.lifecycle.LiveData

interface ReadMoreViewModel {

    val errorLivedata: LiveData<String>
    val progressLivedata: LiveData<Boolean>
    val favouriteScreenLiveData: LiveData<Boolean>
    val isFavouriteLiveData: LiveData<Boolean>

    fun changeFavourite ( isFav : Boolean)
    fun gotoFavouriteScreen ()

}