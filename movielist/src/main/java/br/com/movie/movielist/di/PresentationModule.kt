package br.com.movie.movielist.di

import br.com.movie.movielist.presentation.viewmodel.MovieListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieListViewModel(get()) }
}
