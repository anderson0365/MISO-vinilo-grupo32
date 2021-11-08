package com.miso_vinilo_grupo32.models

data class Album (
    val albumId:Int,
    val name:String,
    val cover:String,
    val releaseDate:String,
    val description:String,
    val genre:String,
    val recordLabel:String,
    val songs: MutableList<Song>
)

data class Song(
    val songId: Int,
    val name: String,
    val duration: String
)

