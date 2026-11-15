package iesam.dam2.spotifyapp.feature.songs.domain

class GetAllSongsUseCase (private val songRepository: SongRepository){

    operator fun invoke(): List <Song>{
        return songRepository.getAll()
    }
}