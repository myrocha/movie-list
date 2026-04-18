package br.com.movie.movielist.di

import br.com.movie.movielist.data.mapper.MovieMapper
import br.com.movie.movielist.data.mapper.MovieMapperImpl
import br.com.movie.movielist.data.repository.MovieRepositoryImpl
import br.com.movie.movielist.domain.repository.MovieRepository
import br.com.movie.movielist.domain.usecase.GetMyListsUseCase
import org.koin.dsl.module

val movieListModule = module {

    factory<MovieMapper> { MovieMapperImpl() }

    single<MovieRepository> { MovieRepositoryImpl(service = get(), mapper = get()) }

    factory { GetMyListsUseCase(get()) }
}