package br.com.movie.movielist.domain.usecase

import br.com.movie.movielist.domain.model.Movie
import br.com.movie.movielist.domain.model.Resource
import br.com.movie.movielist.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class GetMyListsUseCase(private val repository: MovieRepository) {

    operator fun invoke(page: Int): Flow<Resource<List<Movie>>> {
        return repository.getMyLists(page)
            .map { movies ->
                Resource.Success(movies) as Resource<List<Movie>>
            }
            .onStart {
                emit(Resource.Loading)
            }
            .catch { exception ->
                emit(Resource.Error(exception))
            }
    }
}