package com.example.filmfinder.view

import com.example.filmfinder.data.MovieDTO

interface OnItemClickListener {
    fun onItemClick(movie: MovieDTO)
}