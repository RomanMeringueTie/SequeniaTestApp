package com.sequenia.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.sequenia.movies.ui.movie_details.MovieDetailsFragment
import com.sequenia.movies.ui.movie_list.MovieListFragment
import kotlinx.serialization.Serializable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment

        val navController = navHostFragment.navController

        navController.graph = navController.createGraph(
            startDestination = MovieListRoute
        ) {
            fragment<MovieListFragment, MovieListRoute> { }
            fragment<MovieDetailsFragment, MovieDetailsRoute> { }
        }

    }
}

@Serializable
data object MovieListRoute

@Serializable
data object MovieDetailsRoute