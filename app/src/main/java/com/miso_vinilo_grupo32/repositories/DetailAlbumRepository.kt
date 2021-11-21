package com.miso_vinilo_grupo32.repositories

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.miso_vinilo_grupo32.models.Album
import com.miso_vinilo_grupo32.network.CacheManager
import com.miso_vinilo_grupo32.network.NetworkServiceAdapter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DetailAlbumRepository (val application: Application) {

    suspend fun refreshData(albumId:Int): Album{
        val potentialResp = CacheManager.getInstance(application.applicationContext).getAlbum(albumId)
        if (potentialResp == null) {
            val album = NetworkServiceAdapter.getInstance(application).getAlbum(albumId)
            CacheManager.getInstance(application.applicationContext).addAlbum(album)
            return album
        }
        return potentialResp!!
    }
}