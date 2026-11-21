package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.model.Outfit
import com.example.data.model.UserProfile
import com.example.data.model.WardrobeItem

@Database(
    entities = [WardrobeItem::class, Outfit::class, UserProfile::class],
    version = 1,
    exportSchema = false
)
abstract class StylloDatabase : RoomDatabase() {
    abstract fun wardrobeDao(): WardrobeDao
    abstract fun outfitDao(): OutfitDao
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: StylloDatabase? = null

        fun getDatabase(context: Context): StylloDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StylloDatabase::class.java,
                    "styllo_atelier.db"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
