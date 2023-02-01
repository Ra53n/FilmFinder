package com.example.filmfinder.presentation.view

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.api.load
import com.example.filmfinder.R
import com.example.filmfinder.databinding.DetailsFragmentBinding
import com.example.filmfinder.presentation.model.event.DetailsEvent
import com.example.filmfinder.presentation.viewModel.DetailsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named
import org.koin.dsl.module

const val DETAILS_FRAGMENT_BUNDLE_KEY = "KEY"

class DetailsFragment : Fragment(R.layout.details_fragment) {

    private val binding: DetailsFragmentBinding by viewBinding()
    private val viewModel: DetailsViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUiState()
        initListeners(view)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loadKoinModules(module {
            single(named("detailsMovieId")) {
                arguments?.getString(
                    DETAILS_FRAGMENT_BUNDLE_KEY
                )
            }
        })
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    private fun initListeners(view: View) {
        view.setOnLongClickListener {
            val popupMenu = PopupMenu(context, it, Gravity.END)
            popupMenu.setOnMenuItemClickListener {
                viewModel.sendEvent(DetailsEvent.MenuItemNoteAddClick)
                true
            }
            popupMenu.inflate(R.menu.details_fragment_menu)
            popupMenu.show()
            true
        }
    }

    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.viewStateObserve.collect { state ->
                state.movie?.let {
                    with(binding) {
                        movieName.text = it.title
                        movieDescription.text = it.plot
                        movieDescription.movementMethod = ScrollingMovementMethod()
                        movieYear.text = it.year
                        movieRating.text = it.imDBRating
                        movieImage.load(it.image)
                    }
                }
                if (state.showError) {
                    showError()
                }
            }
        }
    }

    private fun showError() {
        Toast.makeText(
            requireContext(),
            resources.getString(R.string.something_gone_wrong),
            Toast.LENGTH_SHORT
        ).show()
    }
}