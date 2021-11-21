package com.miso_vinilo_grupo32.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.miso_vinilo_grupo32.models.SimpleArtist
import com.miso_vinilo_grupo32.repositories.ListArtistRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ListArtistVM(application: Application) :  AndroidViewModel(application) {

    private val _artists = MutableLiveData<MutableList<SimpleArtist>>()

    private val listArtistRepository = ListArtistRepository(application)

    val artists: LiveData<MutableList<SimpleArtist>>
        get() = _artists

    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        refreshDataFromNetwork()
    }

    private fun refreshDataFromNetwork() {
        try{
            viewModelScope.launch (Dispatchers.Default) {
                val data = listArtistRepository.refreshData()
                _artists.postValue(data)
            }
            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false
        }catch (e: Exception){
            _eventNetworkError.value = true
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ListArtistVM::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ListArtistVM(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}