package com.sequenia.movies.ui.movie_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.navOptions
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sequenia.movies.R
import com.sequenia.movies.model.Movie
import com.sequenia.movies.ui.navigation.Route
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MovieListAdapter(
    private val items: List<Movie>,
    private val navController: NavController
) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>(), KoinComponent {

    private val context: Context by inject()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.movie_item_title)
        val imageView: ImageView = view.findViewById(R.id.movie_item_image)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.movie_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textView.text = items[position].localizedName
        viewHolder.itemView.setOnClickListener {
            val options = navOptions {
                anim {
                    enter = R.anim.fade_in
                    popEnter = R.anim.fade_in
                }
            }
            val movieJson = Json.encodeToString(items[position])
            val route = Route.MovieDetailsRoute(movieJson)
            navController.navigate(route = route, navOptions = options)
        }
        val emptyImage = ContextCompat.getDrawable(
            context,
            R.drawable.no_image
        )
        items[position].imageUrl?.let {
            Glide.with(context)
                .load(it)
                .error(emptyImage)
                .into(viewHolder.imageView)
        } ?: viewHolder.imageView.setImageDrawable(emptyImage)
    }

    override fun getItemCount() = items.size

}