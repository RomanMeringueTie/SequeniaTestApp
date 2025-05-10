package com.sequenia.movies

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.sequenia.movies.ui.movie_details.MovieDetailsFragment
import com.sequenia.movies.ui.movie_list.MovieListFragment
import com.sequenia.movies.ui.navigation.Route

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment

        val navController = navHostFragment.navController

        navController.graph = navController.createGraph(
            startDestination = Route.MovieListRoute
        ) {
            fragment<MovieListFragment, Route.MovieListRoute> {}
            fragment<MovieDetailsFragment, Route.MovieDetailsRoute> {
                argument("movie") {
                    type = NavType.StringType
                }
            }
        }

    }
}
