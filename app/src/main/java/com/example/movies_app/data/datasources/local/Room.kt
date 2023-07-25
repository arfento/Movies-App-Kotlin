package com.example.movies_app.data.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movies_app.data.models.MovieDao
import com.example.movies_app.data.models.MoviesEntity
import com.example.movies_app.data.models.MoviesEntityConvertor

@Database(entities = [MoviesEntity::class], version = 1, exportSchema = false)
@TypeConverters(MoviesEntityConvertor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}