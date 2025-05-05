package com.sequenia.movies.ui.movie_list

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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sequenia.movies.R
import com.sequenia.movies.databinding.FragmentMovieListBinding
import com.sequenia.movies.ui.utils.dp
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get

class MovieListFragment : Fragment() {

    private val viewModel: MovieListViewModel = get()

    private lateinit var binding: FragmentMovieListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentMovieListBinding.inflate(inflater, container, false)

        val itemDecoration = GridSpacingItemDecoration(
            spanCount = 2,
            spacing = 10.dp(resources),
            includeEdge = false
        )
        binding.movieList.addItemDecoration(itemDecoration)

        val genresAdapter = GenreListAdapter(viewModel)

        binding.genreContainer.adapter = genresAdapter

        collector()

        return binding.root
    }

    private fun collector() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect {
                    when (it.state) {
                        is State.Content -> {
                            val movieList = it.pickedMovies
                            val adapter = MovieListAdapter(requireContext(), movieList)
                            binding.movieList.adapter = adapter
                            setVisibility(isContentVisible = true)
                        }

                        is State.Error -> {
                            setVisibility(isContentVisible = false)
                            binding.progressIndicator.visibility = View.GONE

                            val dialog = BottomSheetDialog(requireContext())
                            dialog.window?.clearFlags(FLAG_DIM_BEHIND)
                            val bottomSheet =
                                layoutInflater.inflate(R.layout.error_dialog, binding.root, false)
//                            bottomSheet.findViewById<TextView>(R.id.error_text).text = it.state.message
                            bottomSheet.findViewById<TextView>(R.id.error_button)
                                .setOnClickListener {
                                    dialog.dismiss()
                                    viewModel.retry()
                                }
                            dialog.setContentView(bottomSheet)
                            dialog.show()

                        }

                        State.Loading -> {
                            setVisibility(isContentVisible = false)
                        }
                    }
                }
            }
        }
    }

    private fun setVisibility(isContentVisible: Boolean) {

        val contentVisibility = if (isContentVisible) View.VISIBLE else View.GONE
        val loaderVisibility = if (isContentVisible) View.GONE else View.VISIBLE

        binding.genresHeader.visibility = contentVisibility
        binding.genreContainer.visibility = contentVisibility
        binding.moviesHeader.visibility = contentVisibility
        binding.movieList.visibility = contentVisibility

        binding.progressIndicator.visibility = loaderVisibility
    }

}