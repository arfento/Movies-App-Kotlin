package com.example.movies_app.di

import com.example.movies_app.data.repository.MovieRepositoryImpl
import com.example.movies_app.domain.repository.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    internal abstract fun bindMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository
}