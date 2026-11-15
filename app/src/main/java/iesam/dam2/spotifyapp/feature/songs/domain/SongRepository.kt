package iesam.dam2.spotifyapp.feature.songs.domain

interface SongRepository {

    fun getAll () : List <Song>
}