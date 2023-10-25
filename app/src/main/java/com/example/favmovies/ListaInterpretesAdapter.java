package com.example.favmovies;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.favmovies.modelo.Interprete;
import com.example.favmovies.modelo.Pelicula;
import com.example.favmovies.modelo.PeliculaConReparto;
import com.example.favmovies.util.ImageManager;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;


public class ListaInterpretesAdapter extends RecyclerView.Adapter<ListaInterpretesAdapter.InterpreteViewHolder> {

    private PeliculaConReparto pelicula;


    public ListaInterpretesAdapter(PeliculaConReparto pelicula) {
        this.pelicula = pelicula;
    }

    @NonNull
    @Override
    public InterpreteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Creamos la vista con el layout para un elemento
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linea_recycler_view_interprete, parent, false);
        return new InterpreteViewHolder(itemView);
    }


    /** Asocia el contenido a los componentes de la vista,
     * concretamente con nuestro PeliculaViewHolder que recibimos como parámetro
     */
    @Override
    public void onBindViewHolder(@NonNull InterpreteViewHolder holder, int position) {
        // llama al método de nuestro holder para asignar valores a los componentes
        // además, pasamos el listener del evento onClick
        holder.bindUser(pelicula.getReparto().get(position));
    }

    @Override
    public int getItemCount() {
        return pelicula.getReparto().size();
    }



    public static class InterpreteViewHolder extends RecyclerView.ViewHolder{

        private TextView nombre;
        private ImageView imagen;

        public InterpreteViewHolder(View itemView) {
            super(itemView);

            nombre = (TextView) itemView.findViewById(R.id.nombre_interprete);
            imagen= (ImageView)itemView.findViewById(R.id.imagen_interprete);


        }

        // asignar valores a los componentes
        public void bindUser(final Interprete interprete) {
            nombre.setText(interprete.getNombre());

            // cargar imagen
            Picasso.get().load(ImageManager.getImageCaratula(interprete.getImagen()))
                    .into(imagen);

        }
    }

}