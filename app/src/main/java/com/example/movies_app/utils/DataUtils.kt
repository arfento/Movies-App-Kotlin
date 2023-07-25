package com.example.movies_app.utils

import com.example.movies_app.data.datasources.remote.response.MovieListOffers
import com.example.movies_app.data.datasources.remote.response.MovieListResponse
import com.example.movies_app.domain.entities.Currency
import com.example.movies_app.domain.entities.Image
import com.example.movies_app.domain.entities.Movie
import com.example.movies_app.domain.entities.Price

fun createMovies(
    movieDetails: List<MovieListResponse.MovieData>,
    movieListOffers: MovieListOffers
): List<Movie> {
    return movieListOffers.offers.mapNotNull { offers ->
        val details = movieDetails.find { it.movieId == offers.movieId }
        details?.let {
            val movieOfferPrice = offers.price
            val currency = Currency(movieOfferPrice.last().toString())
            val price = movieOfferPrice.substring(0 until movieOfferPrice.length - 1).toFloat()
            Movie(
                id = it.movieId,
                title = it.title,
                subtitle = it.subTitle,
                price = Price(price, currency),
                image = Image(movieListOffers.imageBase + offers.image),
                availability = offers.available
            )
        }
    }
}