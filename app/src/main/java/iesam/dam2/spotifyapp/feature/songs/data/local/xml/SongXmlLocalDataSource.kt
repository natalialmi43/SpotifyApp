package iesam.dam2.spotifyapp.feature.songs.data.local.xml

import android.content.Context
import edu.iesam.marvelapp.core.serialization.JsonSerializer
import iesam.dam2.spotifyapp.R
import iesam.dam2.spotifyapp.core.cache.AppCache
import iesam.dam2.spotifyapp.feature.songs.domain.Song

class SongXmlLocalDataSource (
    private val context: Context,
    private val serializer: JsonSerializer <List<Song>>,
    private val appCache: AppCache
) {


    private val keySong : String = "Song"
    private val ttl : Long = 5*60*1000

    val sharedPref = context.getSharedPreferences(context.getString(R.string.student_xml), Context.MODE_PRIVATE)


    fun save (song: List <Song>){
        sharedPref.edit().apply{
            putString(keySong, serializer.toJson(song))
            apply()
        }

        appCache.updateTime(keySong)
    }


    fun findAll() : List <Song>{

        if(!appCache.isExpired(keySong, ttl)){
            val jsonList = sharedPref.getString(keySong, null)
            jsonList?.let {
                return serializer.fromJson(it)
            }
        }

        return emptyList()
    }


    fun findById (id: String) : Song? {
        return findAll().find { it.id == id }
    }

    fun deleteBiId ( id : String){
        save(findAll().filterNot { it.id == id })
    }

    fun removeAll (){
        sharedPref.edit().apply{
            remove(keySong)
            apply()
        }
    }

}