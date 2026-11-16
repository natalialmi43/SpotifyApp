package iesam.dam2.spotifyapp.feature.songs.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import iesam.dam2.spotifyapp.feature.songs.domain.GetAllSongsUseCase
import iesam.dam2.spotifyapp.feature.songs.domain.Song

class SongListViewModel (private val getAllSongsUseCase: GetAllSongsUseCase) : ViewModel() {

    data class  UiState (
        val isLoading : Boolean = false,
        val hasError : Boolean = false,
        val songs : List<Song>  ? = null
    )

    private val _uiState = MutableLiveData<UiState>()
    val uiState : LiveData<UiState> = _uiState


    fun loadSongs (){
        _uiState.value = UiState(isLoading = true)
        _uiState.value = UiState(songs = getAllSongsUseCase.invoke())
    }

    companion object {

        fun factory (getAllSongsUseCase: GetAllSongsUseCase) = viewModelFactory(){
            initializer { SongListViewModel(getAllSongsUseCase) }
        }
    }
}