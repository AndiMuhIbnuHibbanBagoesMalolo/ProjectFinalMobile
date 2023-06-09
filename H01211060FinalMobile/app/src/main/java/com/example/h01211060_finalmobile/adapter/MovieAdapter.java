package com.example.h01211060_finalmobile.adapter;

import android.graphics.Movie;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.h01211060_finalmobile.R;
import com.example.h01211060_finalmobile.model.MovieModel;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<MovieModel> movies;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(MovieModel movie);
    }

    public MovieAdapter(List<MovieModel> movies, OnItemClickListener onItemClickListener) {
        this.movies =  movies;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MovieModel movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView ivMovie;
        private TextView tvMovie;
        private TextView tvTahun;

        public ViewHolder(View itemView) {
            super(itemView);
            ivMovie = itemView.findViewById(R.id.posterImageView);
            tvMovie = itemView.findViewById(R.id.titleTextView);
            tvTahun = itemView.findViewById(R.id.yearTextView);

            itemView.setOnClickListener(this);
        }

        public void bind(MovieModel movie) {
            Glide.with(itemView.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                    .into(ivMovie);

            tvMovie.setText(movie.getTitle());
            tvTahun.setText(movie.getReleaseDate());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                MovieModel movie = movies.get(position);
                onItemClickListener.onItemClick(movie);
            }
        }
    }
}
