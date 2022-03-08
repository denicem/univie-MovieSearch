package com.denice.a3_moviesearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {
    private ArrayList<Favourite> favList;
    private OnItemClickListener favListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        favListener = listener;
    }

    public static class FavViewHolder extends RecyclerView.ViewHolder {
        public ImageView favPoster;
        public TextView favTitle;
        public TextView favYear;
        public TextView favGenre;
        FavViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            favPoster = itemView.findViewById(R.id.favPoster);
            favTitle =  itemView.findViewById(R.id.favMovieTitle);
            favYear = itemView.findViewById(R.id.favYear);
            favGenre = itemView.findViewById(R.id.FavGenre);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public FavAdapter(ArrayList<Favourite> f) {
        favList = f;
    }

    @NotNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_favourite, parent, false);
        FavViewHolder fvh = new FavViewHolder(v, favListener);
        return fvh;
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        Favourite currentFav = favList.get(position);

        Picasso.get().load(currentFav.getPoster()).into(holder.favPoster);
        holder.favTitle.setText(currentFav.getTitle());
        holder.favYear.setText(currentFav.getYear());
        holder.favGenre.setText(currentFav.getGenre());
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }
}
