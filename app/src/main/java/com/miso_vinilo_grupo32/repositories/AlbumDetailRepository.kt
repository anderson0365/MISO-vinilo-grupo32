package com.miso_vinilo_grupo32.repositories

import android.app.Application
import com.android.volley.VolleyError
import com.miso_vinilo_grupo32.models.Album
import com.miso_vinilo_grupo32.network.NetworkServiceAdapter

class AlbumDetailRepository (val application: Application) {
    fun refreshData(albumId:Int, onComplete:(resp: Album)->Unit, onError: (error: VolleyError)->Unit){
        NetworkServiceAdapter.getInstance(application).getAlbum(albumId ,{
            onComplete(it)
        },
            onError
        )
    }
}