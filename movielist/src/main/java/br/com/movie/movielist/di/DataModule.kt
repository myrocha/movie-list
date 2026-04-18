package br.com.movie.movielist.di

import br.com.movie.movielist.data.repository.MovieRepositoryImpl
import br.com.movie.movielist.domain.repository.MovieRepository
import br.com.movie.movielist.domain.usecase.GetMyListsUseCase
import org.koin.dsl.module

val movieListModule = module {

    single<MovieRepository> { MovieRepositoryImpl(get()) }

    factory { GetMyListsUseCase(get()) }
}