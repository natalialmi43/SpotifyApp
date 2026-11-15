package iesam.dam2.spotifyapp.core.cache

import android.content.Context
import iesam.dam2.spotifyapp.R
import iesam.dam2.spotifyapp.core.time.AppTime

interface AppCache{
    fun updateTime(key: String)
    fun isExpired (key : String, ttl: Long) : Boolean
}

class AppCacheTtl (
    private val context: Context,
    private val appTime: AppTime
) : AppCache{


    val sharedPref = context.getSharedPreferences(context.getString(R.string.app_cache), Context.MODE_PRIVATE
    )

    override fun updateTime(key: String) {
        sharedPref.edit().apply{
            putLong(key, appTime.currentTimeToLong())
        }
    }

    override fun isExpired(key: String, ttl: Long): Boolean {

        val timeSaved = sharedPref.getLong(key, -1)

        return (appTime.currentTimeToLong() - timeSaved) >= ttl
    }
}