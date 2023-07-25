package com.example.movies_app.data.repository

import android.content.Context
import com.example.movies_app.utils.Resource
import com.example.movies_app.data.datasources.remote.network.MovieService
import com.example.movies_app.data.datasources.remote.response.MovieListOffers
import com.example.movies_app.data.datasources.remote.response.MovieListResponse
import com.example.movies_app.di.IoDispatcher
import com.example.movies_app.data.models.MovieDao
import com.example.movies_app.data.models.asDomainModel
import com.example.movies_app.domain.repository.MovieRepository
import com.example.movies_app.utils.createMovies
import com.example.movies_app.domain.entities.Movie
import com.example.movies_app.domain.entities.asDatabaseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val context: Context,
    private val dao: MovieDao,
    private val movieService: MovieService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : MovieRepository {

    override fun getMovies(): Flow<Resource<List<Movie>>> = flow {
        try {
            dao.getMovies()?.let {
                emit(Resource.Success(it.asDomainModel()))
                emitMovies(this, true)

            } ?: run { emitMovies(this, false) }


        } catch (t : Throwable) {
            emit(Resource.Error("There is a problem. Please try again later $t"))
        }
    }.flowOn(ioDispatcher)

    private suspend fun emitMovies(
        flow: FlowCollector<Resource<List<Movie>>>, shouldClearDb: Boolean
    ) {
        flow.emit(Resource.Loading)
        coroutineScope {
            val movieDataList: Deferred<MovieListResponse> = async {
                movieService.fetchMovieList()
            }
            val movieListOffers: Deferred<MovieListOffers> = async {
                movieService.fetchMovieListOffers()
            }

            val movies = createMovies(
                movieDataList.await().movieData,
                movieListOffers.await()
            )

            if (shouldClearDb) {
                dao.deleteMovies()
            }
            dao.insert(movies.asDatabaseModel())
            flow.emit(Resource.Success(movies))


        }
    }
}
