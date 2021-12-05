package com.miso_vinilo_grupo32.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.miso_vinilo_grupo32.R

class UserLoginView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        findViewById<Button>(R.id.user_button).setOnClickListener {
            val intent = Intent(this, MainView::class.java)
            startActivity(intent)
        }
    }
}