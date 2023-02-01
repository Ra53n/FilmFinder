package com.example.filmfinder.presentation.navigation

import android.os.Bundle
import androidx.navigation.NavController
import com.example.filmfinder.R
import com.example.filmfinder.domain.model.MovieEntity
import com.example.filmfinder.presentation.view.DETAILS_FRAGMENT_BUNDLE_KEY
import com.example.filmfinder.presentation.view.NOTE_ADD_BUNDLE_KEY

class Navigator(private val navController: NavController) {
    fun navigateToDetails(movieId: String) {
        navController.navigate(R.id.details, Bundle().apply {
            putSerializable(DETAILS_FRAGMENT_BUNDLE_KEY, movieId)
        })
    }

    fun navigateToMaps() {
        navController.navigate(R.id.mapsFragment)
    }

    fun navigateToContacts() {
        navController.navigate(R.id.contentProvider)
    }

    fun navigateToNotes() {
        navController.navigate(R.id.notesFragment)
    }

    fun navigateToLikedMovies() {
        navController.navigate(R.id.likedMovieFragment)
    }

    fun navigateToAddNote(movie: MovieEntity) {
        navController.navigate(R.id.noteAddFragment, Bundle().apply {
            putSerializable(NOTE_ADD_BUNDLE_KEY, movie)
        })
    }
}