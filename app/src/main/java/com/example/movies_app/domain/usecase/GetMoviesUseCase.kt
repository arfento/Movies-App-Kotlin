package com.example.movies_app.domain.usecase

import com.example.movies_app.domain.repository.MovieRepository
import com.example.movies_app.utils.Resource
import com.example.movies_app.domain.entities.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<Resource<List<Movie>>> =
        movieRepository.getMovies()
}