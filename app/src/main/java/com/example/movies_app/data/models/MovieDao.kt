package com.example.movies_app.data.models

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: MoviesEntity)

    @Query("Select * from movies")
    suspend fun getMovies(): MoviesEntity?

    @Query("Delete from movies")
    suspend fun deleteMovies()
}