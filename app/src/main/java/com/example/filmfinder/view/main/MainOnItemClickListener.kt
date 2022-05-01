package com.example.filmfinder.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.filmfinder.R
import com.example.filmfinder.data.MovieDTO
import com.example.filmfinder.view.OnItemClickListener
import com.example.filmfinder.view.details.DETAILS_FRAGMENT_BUNDLE_KEY
import com.example.filmfinder.view.details.DetailsFragment

class MainOnItemClickListener(private val fragmentActivity: Fragment) : OnItemClickListener {
    override fun onItemClick(movie: MovieDTO) {
        fragmentActivity.activity?.let {
            it.supportFragmentManager.beginTransaction()
                .add(
                    R.id.container,
                    DetailsFragment.newInstance(Bundle().apply {
                        putParcelable(
                            DETAILS_FRAGMENT_BUNDLE_KEY, movie
                        )
                    })
                )
                .addToBackStack("").commit()
        }
    }
}