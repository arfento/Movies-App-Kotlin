package com.example.movies_app.domain.repository

import com.example.movies_app.utils.Resource
import com.example.movies_app.domain.entities.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies() : Flow<Resource<List<Movie>>>
}