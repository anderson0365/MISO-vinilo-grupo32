package com.miso_vinilo_grupo32.network

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.miso_vinilo_grupo32.models.Album
import com.miso_vinilo_grupo32.models.Song

class NetworkServiceAdapter constructor(context: Context) {
    companion object{
        const val BASE_URL= "http://192.168.72.129:3000/"
        var instance: NetworkServiceAdapter? = null
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
    fun getAlbum(albumId: Int, onComplete:(resp: Album )->Unit , onError: (error:VolleyError)->Unit){
        requestQueue.add(getRequest("albums/${albumId}",
            Response.Listener<String> { response ->
                val item = JSONObject(response)
                val songs = mutableListOf<Song>()
                val songsToProcess = item.getJSONArray("tracks")
                for (i in 0 until songsToProcess.length()){
                    val song = songsToProcess.getJSONObject(i)
                    songs.add(i, Song(songId = song.getInt("id"), name= song.getString("name"), duration = song.getString("duration")))
                }
                val album = Album(albumId = item.getInt("id"),name = item.getString("name"), cover = item.getString("cover"), recordLabel = item.getString("recordLabel"), releaseDate = item.getString("releaseDate"), genre = item.getString("genre"), description = item.getString("description"), songs = songs)
                onComplete(album)
            },
            Response.ErrorListener {
                onError(it)
            }))
    }

    private fun getRequest(path:String, responseListener: Response.Listener<String>, errorListener: Response.ErrorListener): StringRequest {
        return StringRequest(Request.Method.GET, BASE_URL+path, responseListener,errorListener)
    }
    private fun postRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.POST, BASE_URL+path, body, responseListener, errorListener)
    }
    private fun putRequest(path: String, body: JSONObject,  responseListener: Response.Listener<JSONObject>, errorListener: Response.ErrorListener ):JsonObjectRequest{
        return  JsonObjectRequest(Request.Method.PUT, BASE_URL+path, body, responseListener, errorListener)
    }
}