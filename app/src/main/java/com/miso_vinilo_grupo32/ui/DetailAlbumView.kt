package com.miso_vinilo_grupo32.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.miso_vinilo_grupo32.databinding.ActivityAlbumDetailBinding
import com.miso_vinilo_grupo32.models.Album
import com.miso_vinilo_grupo32.viewmodels.DetailAlbumVM
import android.view.View
import android.view.Window
import android.widget.*
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.miso_vinilo_grupo32.R

class DetailAlbumView : AppCompatActivity() {

    private lateinit var binding: ActivityAlbumDetailBinding
    private lateinit var AlbumVM: DetailAlbumVM

    private var watchingSongsList = false
    private lateinit var songListButton: RelativeLayout
    private lateinit var basicContent: LinearLayout
    private lateinit var arrowUpImageView: ImageView
    private lateinit var arrowDownImageView: ImageView
    private lateinit var songListLayout: LinearLayout
    private lateinit var context: DetailAlbumView

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
            val intent = Intent(this, MainView::class.java)
            startActivity(intent)
        }

        val cover: ImageView = findViewById(R.id.album_image)

        val albumId = intent?.extras?.getInt("albumId")!!

        AlbumVM = ViewModelProvider(this, DetailAlbumVM.Factory(application, albumId)).get(
            DetailAlbumVM::class.java)
        AlbumVM.album.observe(this, {
            it.apply {
                binding.album = this
                binding.genreText = "(${this.genre})"
                val release_date = this.releaseDate.substring(0, 10).split("-")
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

                Glide.with(context)
                    .load(this.cover.toUri().buildUpon().scheme("https").build())
                    .apply(
                        RequestOptions()
                        .placeholder(R.drawable.ic_downloading_image_foreground)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.ic_broken_image_foreground))
                    .into(cover)

            }
        })
        AlbumVM.eventNetworkError.observe(this,  { isNetworkError ->
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
        if(!AlbumVM.isNetworkErrorShown.value!!) {
            Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
            AlbumVM.onNetworkErrorShown()
        }
    }

}