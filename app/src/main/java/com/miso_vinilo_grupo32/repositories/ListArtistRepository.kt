package com.miso_vinilo_grupo32.repositories

import android.app.Application
import com.miso_vinilo_grupo32.models.SimpleArtist
import com.miso_vinilo_grupo32.network.CacheManager
import com.miso_vinilo_grupo32.network.NetworkServiceAdapter

class ListArtistRepository (val application: Application) {

    suspend fun refreshData(): MutableList<SimpleArtist> {
        val potentialResp = CacheManager.getInstance(application.applicationContext).getSimpleArtists()
        if (potentialResp.isEmpty()) {
            val artists = NetworkServiceAdapter.getInstance(application).getSimpleArtists()
            CacheManager.getInstance(application.applicationContext).addSimpleArtists(artists)
            return artists
        }
        return potentialResp
    }
}