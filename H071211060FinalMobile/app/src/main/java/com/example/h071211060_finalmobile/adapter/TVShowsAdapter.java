package com.example.h071211060_finalmobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.h071211060_finalmobile.R;
import com.example.h071211060_finalmobile.TVShowsDetailsActivity;
import com.example.h071211060_finalmobile.models.tvshows.TVShowsResults;

import java.util.List;

public class TVShowsAdapter extends RecyclerView.Adapter<TVShowsAdapter.TVShowsViewHolder> {

    private Context context;
    private List<TVShowsResults> tvshowsResultList;

    public TVShowsAdapter(Context context, List<TVShowsResults> tvshowsResultList) {
        this.context = context;
        this.tvshowsResultList = tvshowsResultList;
    }

    public void addTelevShows(List<TVShowsResults> newTelevShows) {
        int startPos = tvshowsResultList.size();
        tvshowsResultList.addAll(newTelevShows);
        notifyItemRangeInserted(startPos, newTelevShows.size());
    }



    @NonNull
    @Override
    public TVShowsAdapter.TVShowsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        view = inflater.inflate(R.layout.item_tvshows, parent, false );
        TVShowsAdapter.TVShowsViewHolder viewHolder = new TVShowsAdapter.TVShowsViewHolder(view);
        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(parent.getContext(), TVShowsDetailsActivity.class);
                TVShowsResults tvshowsResult = new TVShowsResults();
                tvshowsResult.setName(tvshowsResultList.get(viewHolder.getAdapterPosition()).getName());
                tvshowsResult.setOverview(tvshowsResultList.get(viewHolder.getAdapterPosition()).getOverview());
                tvshowsResult.setBackdropPath(tvshowsResultList.get(viewHolder.getAdapterPosition()).getBackdropPath());
                tvshowsResult.setVoteAverage(tvshowsResultList.get(viewHolder.getAdapterPosition()).getVoteAverage());
                tvshowsResult.setFirstAirDate(tvshowsResultList.get(viewHolder.getAdapterPosition()).getFirstAirDate());
                tvshowsResult.setPosterPath(tvshowsResultList.get(viewHolder.getAdapterPosition()).getPosterPath());
                intent.putExtra(TVShowsResults.EXTRA_TV_SHOW, tvshowsResult);
                parent.getContext().startActivity(intent);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowsAdapter.TVShowsViewHolder holder, int position) {
        holder.tvTitle.setText(tvshowsResultList.get(position).getName());
        String releaseDate = tvshowsResultList.get(position).getFirstAirDate();
        if (releaseDate != null && !releaseDate.isEmpty()) {
            String year = releaseDate.substring(0, 4);
            holder.tvYear.setText(year);
        } else {
            holder.tvYear.setText("");
        }
        Glide.with(context).load("https://image.tmdb.org/t/p/w185" + tvshowsResultList.get(position).getPosterPath())
                .into(holder.tvPoster)
        ;
    }

    @Override
    public int getItemCount() {
        return tvshowsResultList.size();
    }

    public class TVShowsViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout relativeLayout;
        ImageView tvPoster;
        TextView tvTitle,tvYear;

        public TVShowsViewHolder(@NonNull View itemView) {
            super(itemView);

            relativeLayout = itemView.findViewById(R.id.televLayout);
            tvPoster = itemView.findViewById(R.id.televImg);
            tvTitle = itemView.findViewById(R.id.telev_title);
            tvYear = itemView.findViewById(R.id.telev_year);
        }
    }
}
