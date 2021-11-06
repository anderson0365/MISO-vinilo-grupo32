package com.miso_vinilo_grupo32.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.miso_vinilo_grupo32.databinding.ActivityAlbumDetailBinding
import com.miso_vinilo_grupo32.models.Album
import com.miso_vinilo_grupo32.viewmodels.AlbumDetailViewModel
import android.view.View
import android.view.Window
import android.widget.*
import androidx.core.view.setPadding
import com.miso_vinilo_grupo32.R
import com.squareup.picasso.Picasso
import java.io.IOException
import java.net.MalformedURLException


class AlbumDetail : AppCompatActivity() {

    private lateinit var binding: ActivityAlbumDetailBinding
    private lateinit var viewModel: AlbumDetailViewModel

    private var watchingSongsList = false
    private lateinit var songListButton: RelativeLayout
    private lateinit var basicContent: LinearLayout
    private lateinit var arrowUpImageView: ImageView
    private lateinit var arrowDownImageView: ImageView
    private lateinit var songListLayout: LinearLayout
    private lateinit var context: AlbumDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        binding = ActivityAlbumDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        context = this

        arrowUpImageView = findViewById(R.id.arrow_up)
        arrowDownImageView = findViewById(R.id.arrow_down)
        basicContent = findViewById(R.id.basic_content)
        songListLayout = findViewById(R.id.songs_layout)

        songListButton = findViewById(R.id.songs_display)
        songListButton.setOnClickListener{
            showSongList()
        }

        findViewById<Button>(R.id.back_button_album_detail).setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val cover: ImageView = findViewById(R.id.album_image)

        val albumId = intent?.extras?.getInt("albumId")!!

        viewModel = ViewModelProvider(this, AlbumDetailViewModel.Factory(application, albumId)).get(
            AlbumDetailViewModel::class.java)
        viewModel.album.observe(this, Observer<Album> {
            it.apply {
                binding.album = this
                binding.genreText = "(${this.genre})"
                var release_date = this.releaseDate.substring(0, 10).split("-")
                binding.releaseDate = " ${release_date[2]}/${release_date[1]}/${release_date[0]}"
                binding.recordLabel = " ${this.recordLabel}"

                songListLayout.removeAllViews()

                for( song in this.songs){
                    val textView = TextView(context)
                    textView.text = "${song.name} (${song.duration})"
                    textView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                    textView.textSize = 16F
                    textView.setPadding(25, 5,0,10)

                    songListLayout.addView(textView)
                }

                try {
                    Picasso.get().load(this.cover).into(cover)
                } catch (e: MalformedURLException) {
                    e.printStackTrace()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        })
        viewModel.eventNetworkError.observe(this, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

    }

    private fun showSongList(){
        if(watchingSongsList){
            arrowUpImageView.visibility = View.VISIBLE
            arrowDownImageView.visibility = View.GONE
            basicContent.visibility = View.VISIBLE
            songListLayout.visibility = View.GONE
            watchingSongsList = false
        }else{
            arrowUpImageView.visibility = View.GONE
            arrowDownImageView.visibility = View.VISIBLE
            basicContent.visibility = View.GONE
            songListLayout.visibility = View.VISIBLE
            watchingSongsList = true
        }
    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

}