package com.miso_vinilo_grupo32.network

import android.content.Context
import android.util.LruCache
import com.miso_vinilo_grupo32.models.Album
import com.miso_vinilo_grupo32.models.SimpleArtist

class CacheManager (context: Context){

    companion object{
        private var instance: CacheManager? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this){
                instance ?: CacheManager(context).also {
                    instance = it
                }
            }
    }

    private val albumCacheSize = 3
    private var simpleArtists: MutableList<SimpleArtist> = mutableListOf()
    private var albums: LruCache<Int, Album> = LruCache(albumCacheSize)

    fun addAlbum(newAlbum: Album){
        if(albums.get(newAlbum.albumId) == null){
            albums.put(newAlbum.albumId, newAlbum)
        }
    }

    fun getAlbum(albumId: Int) : Album?{
        val album = albums.get(albumId)
        return album?: null
    }

    fun addSimpleArtists( newSimpleArtists: MutableList<SimpleArtist>){
        simpleArtists = newSimpleArtists
    }

    fun getSimpleArtists() : MutableList<SimpleArtist> {
        return simpleArtists
    }
}