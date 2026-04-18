package br.com.movie.movielist.di

import br.com.movie.movielist.data.mapper.MovieMapper
import br.com.movie.movielist.data.mapper.MovieMapperImpl
import br.com.movie.movielist.data.repository.MovieRepositoryImpl
import br.com.movie.movielist.domain.repository.MovieRepository
import br.com.movie.movielist.domain.usecase.GetMyListsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val movieListModule = module {

    single<CoroutineDispatcher> { Dispatchers.IO }

    factory<MovieMapper> { MovieMapperImpl() }

    single<MovieRepository> { MovieRepositoryImpl(service = get(), mapper = get(), ioDispatcher = get() ) }

    factory { GetMyListsUseCase(get()) }
}