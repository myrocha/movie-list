package br.com.movie.list

import android.app.Application
import br.com.movie.movielist.di.movieListModule
import br.com.movie.movielist.di.networkModule
import br.com.movie.movielist.di.viewModelModule

import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MovieApplication)

            modules(
                listOf(
                    networkModule,
                    movieListModule,
                    viewModelModule
                )
            )
        }
    }
}