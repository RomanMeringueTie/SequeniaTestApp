package com.sequenia.movies.ui.movie_list

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sequenia.movies.R
import com.sequenia.movies.databinding.FragmentMovieListBinding
import com.sequenia.movies.presentation.MovieListViewModel
import com.sequenia.movies.presentation.State
import com.sequenia.movies.ui.utils.dp
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieListFragment : Fragment() {

    private val viewModel: MovieListViewModel by viewModel()

    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieListBinding.inflate(inflater, container, false)

        val movieListDecoration = GridSpacingItemDecoration(
            spanCount = 2,
            spacing = 10.dp(resources),
            includeEdge = false
        )
        binding.movieListMovieList.addItemDecoration(movieListDecoration)

        binding.root.setOnRefreshListener {
            viewModel.retry()
        }

        collector()

        return binding.root
    }

    private fun collector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it.state) {
                        is State.Content -> {

                            val genresAdapter = GenreListAdapter(
                                pickedGenre = it.pickedGenre,
                                onClick = { position -> viewModel.pickGenre(position) }
                            )
                            binding.movieListGenreContainer.adapter = genresAdapter
                            val movieList = it.pickedMovies
                            val movieAdapter = MovieListAdapter(
                                movieList,
                                NavHostFragment.findNavController(this@MovieListFragment)
                            )
                            binding.movieListMovieList.adapter = movieAdapter
                            setVisibility(isContentVisible = true)
                            binding.root.isRefreshing = false
                        }

                        is State.Error -> {
                            setVisibility(isContentVisible = false)
                            binding.movieListProgressIndicator.visibility = View.GONE
                            binding.root.isRefreshing = false
                            showErrorDialog()

                        }

                        State.Loading -> {
                            setVisibility(isContentVisible = false)
                            binding.root.isRefreshing = false
                        }
                    }
                }
            }
        }
    }

    private fun setVisibility(isContentVisible: Boolean) {

        val contentVisibility = if (isContentVisible) View.VISIBLE else View.GONE
        val loaderVisibility = if (isContentVisible) View.GONE else View.VISIBLE


        binding.movieListGenresHeader.visibility = contentVisibility
        binding.movieListGenreContainer.visibility = contentVisibility
        binding.movieListMoviesHeader.visibility = contentVisibility
        binding.movieListMovieList.visibility = contentVisibility

        binding.movieListProgressIndicator.visibility = loaderVisibility
    }

    private fun showErrorDialog() {
        val dialog = BottomSheetDialog(requireContext())
        dialog.window?.clearFlags(FLAG_DIM_BEHIND)
        val bottomSheet =
            layoutInflater.inflate(R.layout.error_dialog, binding.root, false)
        dialog.setContentView(bottomSheet)
        val bottomSheetView =
            dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheetView?.setBackgroundColor(Color.TRANSPARENT)
        bottomSheetView?.elevation = 0f
//      bottomSheet.findViewById<TextView>(R.id.error_text).text = it.state.message
        bottomSheet.findViewById<TextView>(R.id.error_button)
            .setOnClickListener {
                dialog.dismiss()
                viewModel.retry()
            }
        dialog.setContentView(bottomSheet)
        dialog.setCancelable(false)
        dialog.show()
    }

}