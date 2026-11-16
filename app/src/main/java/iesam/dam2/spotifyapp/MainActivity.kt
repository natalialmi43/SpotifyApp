package iesam.dam2.spotifyapp

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.iesam.marvelapp.core.serialization.GsonJsonSerializer
import iesam.dam2.spotifyapp.core.cache.AppCacheTtl
import iesam.dam2.spotifyapp.core.time.AppTime
import iesam.dam2.spotifyapp.core.time.LocalAppTime
import iesam.dam2.spotifyapp.feature.songs.data.SongDataRepository
import iesam.dam2.spotifyapp.feature.songs.data.local.xml.SongXmlLocalDataSource
import iesam.dam2.spotifyapp.feature.songs.domain.GetAllSongsUseCase
import iesam.dam2.spotifyapp.feature.songs.domain.Song
import iesam.dam2.spotifyapp.feature.songs.presentation.SongListViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel : SongListViewModel by viewModels {
        SongListViewModel.factory(GetAllSongsUseCase(
            SongDataRepository(
                SongXmlLocalDataSource(
                    this,
                    GsonJsonSerializer.create(),
                    AppCacheTtl(this, LocalAppTime())
                )
            )
        ))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initObserver()

        viewModel.loadSongs()
    }

    private fun initObserver(){
        viewModel.uiState.observe(this){ uiState ->
            bind(uiState)
        }
    }

    private fun bind( uiState: SongListViewModel.UiState){

        val test = findViewById<ConstraintLayout>(R.id.test_layout)

        if (uiState.isLoading){
            test.visibility = View.VISIBLE
        } else{
            test.visibility = View.GONE
        }

        if(uiState.hasError){
            test.visibility = View.VISIBLE
        } else{
            test.visibility = View.GONE
        }

        uiState.songs?.let { songs ->
            if(songs.isEmpty()){
                test.visibility = View.VISIBLE
            } else{
                renderUi(songs.first())
            }
        }

    }

    private fun renderUi( song: Song){

        val songViewLayout = findViewById<ConstraintLayout>(R.id.song_view_layout)

        val labelSongName = findViewById<TextView>(R.id.name_song)
        labelSongName.text = song.name

        val labelSongGenre = findViewById<TextView>(R.id.genre_song)
        labelSongGenre.text = song.genre

    }

    private fun saveSong(){

        val xml = SongXmlLocalDataSource(
            this,
            GsonJsonSerializer.create(),
            AppCacheTtl(this, LocalAppTime())
        )
        val song : List<Song> = listOf(Song("1", "AAA", "AAA"))

        xml.save(song)
    }
}