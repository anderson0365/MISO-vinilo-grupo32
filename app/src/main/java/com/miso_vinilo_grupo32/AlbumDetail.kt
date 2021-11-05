package com.miso_vinilo_grupo32

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.android.volley.Response
import com.miso_vinilo_grupo32.brokers.VolleyBroker

class AlbumDetail : AppCompatActivity() {

    lateinit var volleyBroker: VolleyBroker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_detail)

        volleyBroker = VolleyBroker(this.applicationContext)

        val albumId = intent?.extras?.getInt("albumId")
        val albumView: TextView = findViewById(R.id.album_detail_text)
        albumView.text = albumId.toString()

        volleyBroker.instance.add(
            VolleyBroker.getRequest("albums/${albumId}",
                Response.Listener<String> { response ->
                    // Display the first 500 characters of the response string.
                    albumView.text = response
                },
                Response.ErrorListener {
                    Log.d("TAG", it.toString())
                    albumView.text = "That didn't work!"
                }
            ))



        findViewById<Button>(R.id.album_detail_back_button).setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}