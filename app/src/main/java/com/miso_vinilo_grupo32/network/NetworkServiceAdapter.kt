package com.miso_vinilo_grupo32.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.miso_vinilo_grupo32.models.Album
import com.miso_vinilo_grupo32.models.SimpleArtist
import com.miso_vinilo_grupo32.models.Song
import org.json.JSONArray
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "https://public-back-sandbox-vinyls.herokuapp.com/"
        private var instance: NetworkServiceAdapter? = null
        fun getInstance(context: Context) =
            instance ?: synchronized(this) {
                instance ?: NetworkServiceAdapter(context).also {
                    instance = it
                }
            }
    }
    private val requestQueue: RequestQueue by lazy {
        // applicationContext keeps you from leaking the Activity or BroadcastReceiver if someone passes one in.
        Volley.newRequestQueue(context.applicationContext)
    }
    suspend fun getAlbum(albumId: Int) = suspendCoroutine<Album> { cont ->
        requestQueue.add(getRequest("albums/${albumId}",
            Response.Listener<String> { response ->
                val item = JSONObject(response)
                val songs = mutableListOf<Song>()
                val songsToProcess = item.getJSONArray("tracks")
                for (i in 0 until songsToProcess.length()){
                    val song = songsToProcess.getJSONObject(i)
                    songs.add(i, Song(songId = song.getInt("id"), name= song.getString("name"), duration = song.getString("duration")))
                }
                cont.resume(Album(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description"), songs = songs))
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }

    suspend fun getSimpleArtists() = suspendCoroutine<MutableList<SimpleArtist>> { cont ->
        requestQueue.add(getRequest("musicians",
            Response.Listener<String> { response ->
                val items = JSONArray(response)
                val artists = mutableListOf<SimpleArtist>()
                for (i in 0 until items.length()){
                    val artist = items.getJSONObject(i)
                    artists.add(i, SimpleArtist(id = artist.getInt("id"), name = artist.getString("name"), image = artist.getString("image")))
                }
                cont.resume(artists)
            },
            Response.ErrorListener {
                cont.resumeWithException(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }

    /*private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)
    }*/
}