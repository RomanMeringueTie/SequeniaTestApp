package com.sequenia.movies

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.createGraph
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.fragment
import com.sequenia.movies.ui.movie_details.MovieDetailsFragment
import com.sequenia.movies.ui.movie_list.MovieListFragment
import kotlinx.serialization.Serializable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        setStatusBarColor(window, ContextCompat.getColor(this, R.color.blue))

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment

        val navController = navHostFragment.navController

        navController.graph = navController.createGraph(
            startDestination = MovieListRoute
        ) {
            fragment<MovieListFragment, MovieListRoute> {
            }
            fragment<MovieDetailsFragment, MovieDetailsRoute> {
                argument("movie") {
                    type = NavType.StringType
                }
            }
        }

    }

    // https://stackoverflow.com/a/79338465
    private fun setStatusBarColor(window: Window, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) { // Android 15+
            window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                view.setBackgroundColor(color)
                insets
            }
        } else {
            // For Android 14 and below
            window.statusBarColor = color
        }
    }
}

@Serializable
data object MovieListRoute

@Serializable
data class MovieDetailsRoute(val movie: String)