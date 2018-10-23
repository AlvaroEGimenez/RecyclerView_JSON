
package com.example.alva.recyclerview_json;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

public class AdaptadorAlbum extends RecyclerView.Adapter<AdaptadorAlbum.ViewHolderAlbum> {

    private List<Track> tracks;
    private ClickListener clickListener;

    public AdaptadorAlbum(List<Track> tracks, ClickListener clickListener) {
        this.tracks = tracks;
        this.clickListener = clickListener;
    }


    @NonNull
    @Override
    public ViewHolderAlbum onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.modelo_album, parent, false);
        ViewHolderAlbum viewHolderAlbum = new ViewHolderAlbum(view);
        return viewHolderAlbum;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAlbum holder, int posicion) {
        Track track = tracks.get(posicion);
        holder.bind(track);


    }

    @Override
    public int getItemCount() {
        return tracks.size();
    }



    public class ViewHolderAlbum extends RecyclerView.ViewHolder {
        private TextView textViewnombreTrack;
        private TextView textViewnombreArtista;
        private TextView textViewDuracion;





        public ViewHolderAlbum(@NonNull final View itemView) {
            super(itemView);

            textViewnombreArtista = itemView.findViewById(R.id.textview_artista);
            textViewnombreTrack = itemView.findViewById(R.id.textview_track);
            textViewDuracion = itemView.findViewById(R.id.textview_rank);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Track track = tracks.get(getAdapterPosition());
                    clickListener.onClick(track);

                }
            });


        }

        public void bind(Track track) {
            textViewnombreTrack.setText(track.getNombreTrack());
            textViewnombreArtista.setText(track.getNombreArtista());
            textViewDuracion.setText(track.getDuracion());

        }
    }

    public interface ClickListener{
        void onClick (Track track);
    }



}


