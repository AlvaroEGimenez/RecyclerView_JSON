package com.example.alva.recyclerview_json;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorEjemplo extends RecyclerView.Adapter<AdaptadorEjemplo.EjemploViewHOlder> implements Filterable {
    private Context context;
    private ArrayList<ItemEjemplo> listaEjemplos;
    private ArrayList<ItemEjemplo> listaCompleta;
    private AdaptadorInterface adaptadorInterface;

    public AdaptadorEjemplo(Context context, ArrayList<ItemEjemplo> listaEjemplos, AdaptadorInterface adaptadorInterface) {
        this.listaEjemplos = listaEjemplos;
        this.context = context;
        this.listaCompleta = new ArrayList<>(listaEjemplos);
        this.adaptadorInterface = adaptadorInterface;
    }

    @NonNull
    @Override
    public EjemploViewHOlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.imagen_item, viewGroup, false);
        return new EjemploViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EjemploViewHOlder viewHolder, int posicion) {
        ItemEjemplo itemActual = listaEjemplos.get(posicion);
        String imagenUrl = itemActual.getmIMagenUrl();
        String nombreCreador = itemActual.getmCreador();
        String trackList = itemActual.getmLikes();
        String lanzamineto = itemActual.getmLanzamiento();

        viewHolder.mTextVievCreador.setText(nombreCreador);
        viewHolder.mTextViewLikes.setText("Fans: " + trackList);
        viewHolder.mTextViewLanzamiento.setText("Fecha lanzamiento " + lanzamineto);

        Picasso.get().load(imagenUrl).fit().centerCrop().into(viewHolder.mImageView);


    }

    @Override
    public int getItemCount() {
        return listaEjemplos.size();
    }

    @Override
    public Filter getFilter() {
        return listaFiltrada;
    }

    private Filter listaFiltrada = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ItemEjemplo> listaFiltrada = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                listaFiltrada.addAll(listaCompleta);
            } else {

                String filtro = constraint.toString().toLowerCase().trim();

                for (ItemEjemplo itemEjemplo : listaCompleta) {
                    if (itemEjemplo.getmCreador().toLowerCase().contains(filtro)) {
                        listaFiltrada.add(itemEjemplo);

                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = listaFiltrada;
            return results;
        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            listaEjemplos.clear();
            listaEjemplos.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public interface AdaptadorInterface {
        void iraPantallaAlbum (ItemEjemplo itemEjemplo);
    }

    public class EjemploViewHOlder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextVievCreador;
        public TextView mTextViewLikes;
        public TextView mTextViewLanzamiento;

        public EjemploViewHOlder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.image_view);
            mTextVievCreador = itemView.findViewById(R.id.textview_Creador);
            mTextViewLikes = itemView.findViewById(R.id.textview_Likes);
            mTextViewLanzamiento = itemView.findViewById(R.id.textview_Lanzamineto);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ItemEjemplo itemEjemplo = listaEjemplos.get(getAdapterPosition());
                    adaptadorInterface.iraPantallaAlbum(itemEjemplo);
                }
            });

        }
    }
}
