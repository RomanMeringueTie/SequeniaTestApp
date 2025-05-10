package com.sequenia.movies.ui.movie_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.sequenia.movies.R
import com.sequenia.movies.data.model.Genre

class GenreListAdapter(
    private val pickedGenre: Genre?,
    private val onClick: (Int) -> Unit
) :

    RecyclerView.Adapter<GenreListAdapter.ViewHolder>() {

    private val genres = Genre.getCapitalized()

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.genre_item_text)
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.genre_item, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        position: Int
    ) {
        val genreName = genres[position]
        val isPicked = pickedGenre?.string == genreName.lowercase()
        val backgroundRes = if (isPicked) R.color.yellow else R.color.white

        viewHolder.textView.apply {
            text = genreName

            setBackgroundResource(backgroundRes)

            setOnClickListener {
                viewHolder.itemView.background = backgroundRes.toDrawable()
                val lastPickedGenre = pickedGenre?.ordinal
                onClick(position)
                notifyItemChanged(position)
                lastPickedGenre?.let {
                    notifyItemChanged(it)
                }
            }
        }

    }

    override fun getItemCount(): Int = genres.size

}