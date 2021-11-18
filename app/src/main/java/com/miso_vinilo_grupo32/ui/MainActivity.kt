package com.miso_vinilo_grupo32.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.miso_vinilo_grupo32.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.user_button).setOnClickListener {
            var intent = Intent(this, AlbumDetail::class.java)
            intent.putExtra("albumId", 100)

            startActivity(intent)
        }
    }
}