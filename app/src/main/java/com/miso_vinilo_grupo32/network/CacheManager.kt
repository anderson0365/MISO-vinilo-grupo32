package com.miso_vinilo_grupo32.network

import android.content.Context
import android.content.SharedPreferences
import android.util.LruCache
import com.miso_vinilo_grupo32.models.Album

class CacheManager (context: Context){

    companion object{
        var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this){
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }



    private val albumCacheSize = 3
    private var albums: LruCache<Int, Album> = LruCache(albumCacheSize)

    fun addAlbum(newAlbum: Album){
        if(albums.get(newAlbum.albumId) == null){
            albums.put(newAlbum.albumId, newAlbum)
        }
    }

    fun getAlbum(albumId: Int) : Album?{
        var album = albums.get(albumId)
        return album?: null
    }

    fun clearCache() {
        albums.evictAll()
    }
}