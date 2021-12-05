package com.miso_vinilo_grupo32.repositories

import android.app.Application
import com.miso_vinilo_grupo32.models.Album
import com.miso_vinilo_grupo32.network.CacheManager
import com.miso_vinilo_grupo32.network.NetworkServiceAdapter

class DetailAlbumRepository (val application: Application) {

    suspend fun refreshData(albumId:Int): Album?{
        val potentialResp = CacheManager.getInstance(application.applicationContext).getAlbum(albumId)
        if (potentialResp == null) {
            val album = NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
            if (album != null)
                CacheManager.getInstance(application.applicationContext).addAlbum(album)
            return album
        }
        return potentialResp!!
    }
}