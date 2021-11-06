package com.miso_vinilo_grupo32.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.miso_vinilo_grupo32.databinding.ActivityAlbumDetailBinding
import com.miso_vinilo_grupo32.models.Album
import com.miso_vinilo_grupo32.ui.adapters.AlbumDetailAdapter
import com.miso_vinilo_grupo32.viewmodels.AlbumDetailViewModel

class AlbumDetail : AppCompatActivity() {

    private lateinit var binding: ActivityAlbumDetailBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: AlbumDetailViewModel
    private var viewModelAdapter: AlbumDetailAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlbumDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelAdapter = AlbumDetailAdapter()

        recyclerView = binding.albumsRv
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = viewModelAdapter

        val albumId = intent?.extras?.getInt("albumId")!!

        viewModel = ViewModelProvider(this, AlbumDetailViewModel.Factory(application, albumId)).get(
            AlbumDetailViewModel::class.java)
        viewModel.album.observe(this, Observer<Album> {
            it.apply {
                viewModelAdapter!!.album = this
            }
        })
        viewModel.eventNetworkError.observe(this, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

    }

    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(this, "Network Error", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }
}