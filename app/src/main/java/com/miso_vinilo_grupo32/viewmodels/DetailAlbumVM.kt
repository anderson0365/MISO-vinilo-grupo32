package com.miso_vinilo_grupo32.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.miso_vinilo_grupo32.models.Album
import com.miso_vinilo_grupo32.repositories.DetailAlbumRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class DetailAlbumVM(application: Application, albumId: Int) :  AndroidViewModel(application) {

    private val _album = MutableLiveData<Album>()
    private val id = albumId

    private val albumDetailRepository = DetailAlbumRepository(application)

    val album: LiveData<Album>
        get() = _album

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
                val data = albumDetailRepository.refreshData(id)
                _album.postValue(data)
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

    class Factory(val app: Application, val albumId: Int) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailAlbumVM::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailAlbumVM(app, albumId) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}