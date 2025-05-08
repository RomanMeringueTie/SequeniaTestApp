package com.sequenia.movies.ui.movie_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sequenia.movies.MovieDetailsRoute
import com.sequenia.movies.R
import com.sequenia.movies.model.Movie
import kotlinx.serialization.json.Json

class MovieListAdapter(
    private val context: Context,
    private val items: List<Movie>,
    private val navController: NavController
) :
    RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

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
            val movieJson = Json.encodeToString(items[position])
            val route = MovieDetailsRoute(movieJson)
            navController.navigate(route = route)
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