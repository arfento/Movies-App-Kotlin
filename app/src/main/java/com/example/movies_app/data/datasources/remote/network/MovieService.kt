package com.example.movies_app.data.datasources.remote.network

import com.example.movies_app.data.datasources.remote.response.MovieListOffers
import com.example.movies_app.data.datasources.remote.response.MovieListResponse
import retrofit2.http.GET

interface MovieService {
    @GET("movie_offers.json")
    suspend fun fetchMovieListOffers(): MovieListOffers

    @GET("movie_data.json")
    suspend fun fetchMovieList(): MovieListResponse
}