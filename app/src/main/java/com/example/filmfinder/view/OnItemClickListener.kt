package com.example.filmfinder.view

import com.example.filmfinder.data.MovieListItem

interface OnItemClickListener {
    fun onItemClick(movie: MovieListItem)
}