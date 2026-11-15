package iesam.dam2.spotifyapp.feature.songs.data

import iesam.dam2.spotifyapp.feature.songs.data.local.xml.SongXmlLocalDataSource
import iesam.dam2.spotifyapp.feature.songs.domain.Song
import iesam.dam2.spotifyapp.feature.songs.domain.SongRepository

class SongDataRepository (private val xmlLocalDataSource: SongXmlLocalDataSource) : SongRepository {

    override fun getAll(): List<Song> {
        return xmlLocalDataSource.findAll()
    }
}