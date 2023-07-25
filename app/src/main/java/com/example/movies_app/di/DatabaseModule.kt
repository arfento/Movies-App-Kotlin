package com.example.movies_app.di

import android.content.Context
import androidx.room.Room
import com.example.movies_app.data.datasources.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()
}