package com.sequenia.movies.ui.movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.sequenia.movies.R
import com.sequenia.movies.databinding.FragmentMovieDetailsBinding
import com.sequenia.movies.data.model.Movie
import kotlinx.serialization.json.Json

class MovieDetailsFragment : Fragment() {


    private lateinit var movie: Movie

    private lateinit var binding: FragmentMovieDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val movieString = requireArguments().getString("movie")

        movie = Json.decodeFromString<Movie>(movieString ?: "")

        binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setupUI()

        val navController = NavHostFragment.findNavController(this)

        binding.movieDetailsBackButton.setOnClickListener {
            navController.navigateUp()
        }

        return root
    }

    private fun setupUI() {
        binding.movieDetailsTitle.text = movie.name
        binding.movieDetailsLocalizedTitle.text = movie.localizedName
        binding.movieDetailsGenreYear.text = convertGenreList()
        val ratingView = binding.movieDetailsRatingText
        ratingView.text = movie.rating?.toString() ?: getString(R.string.no_rating)
        binding.movieDetailsDescription.text = movie.description
        val imageView = binding.movieDetailsImage
        val emptyImage = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.no_image
        )
        movie.imageUrl?.let {
            Glide.with(requireContext())
                .load(it)
                .error(emptyImage)
                .into(imageView)
        } ?: imageView.setImageDrawable(emptyImage)
    }

    private fun convertGenreList(): String {

        val genreList = if (movie.genres.isNotEmpty())
            movie.genres.joinToString(separator = ", ") { it.string }
        else getString(R.string.no_genre)

        val year = movie.year

        return "$genreList, $year"
    }

}