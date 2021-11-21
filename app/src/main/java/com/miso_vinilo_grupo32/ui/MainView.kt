package com.miso_vinilo_grupo32.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.miso_vinilo_grupo32.R

class MainView :AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view)
        findViewById<Button>(R.id.back_button_main).setOnClickListener {
            var intent = Intent(this, UserLoginView::class.java)
            startActivity(intent)
        }
    }
}