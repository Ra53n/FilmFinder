package com.example.filmfinder.data.di

import androidx.room.Room
import com.example.filmfinder.data.api.MovieApi
import com.example.filmfinder.data.mapper.ActorResponseToEntityMapper
import com.example.filmfinder.data.mapper.LikedMovieModelMapper
import com.example.filmfinder.data.mapper.MovieNotesModelMapper
import com.example.filmfinder.data.mapper.MovieResponseToEntityMapper
import com.example.filmfinder.data.repository.localRepo.LikedMoviesRepository
import com.example.filmfinder.data.repository.localRepo.LikedMoviesRepositoryImpl
import com.example.filmfinder.data.repository.localRepo.NotesRepository
import com.example.filmfinder.data.repository.localRepo.NotesRepositoryImpl
import com.example.filmfinder.data.repository.remote.MovieRepository
import com.example.filmfinder.data.repository.remote.MovieRepositoryImpl
import com.example.filmfinder.data.room.Database
import com.example.filmfinder.domain.interactor.LikedMovieInteractor
import com.example.filmfinder.presentation.viewModel.*
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val mainModule = module {
    single {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(logging)
        Retrofit.Builder()
            .baseUrl("https://imdb-api.com")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(httpClient.build())
            .build()
            .create(MovieApi::class.java)
    }
    factory { ActorResponseToEntityMapper() }
    factory { MovieResponseToEntityMapper(get()) }
    factory { LikedMovieModelMapper() }
    factory { MovieNotesModelMapper() }

    single {
        Room.databaseBuilder(
            androidContext(),
            Database::class.java,
            DB_NAME
        )
            .build()
    }
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    single<LikedMoviesRepository> { LikedMoviesRepositoryImpl(get(), get()) }
    single<NotesRepository> { NotesRepositoryImpl(get(), get()) }
    single { LikedMovieInteractor(get()) }
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { DetailsViewModel(get(), get(), get(named("detailsMovieId"))) }
    viewModel { NotesViewModel(get()) }
    viewModel { NoteAddViewModel(get(), get(named("noteAddMovie"))) }
    viewModel { LikedMoviesViewModel(get()) }
}

private const val DB_NAME = "AppDatabase.db"