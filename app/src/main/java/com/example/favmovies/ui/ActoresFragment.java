package com.example.favmovies.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.favmovies.ListaInterpretesAdapter;
import com.example.favmovies.R;
import com.example.favmovies.datos.AppDatabase;
import com.example.favmovies.modelo.PeliculaConReparto;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActoresFragment extends Fragment {

    private static final String ID_PELICULA="Id pelicula";
    private PeliculaConReparto actoresPeli;
    private int idPelicula;


    /*

        Esto es un FactoryMethod.
        Los datos están siendo enviados ANTES del onCreate.
        El Bundle permanece cuando se tiene que recrear.
     */
    public static ActoresFragment newInstance(int idPelicula) {
        ActoresFragment fragment = new ActoresFragment();
        Bundle args = new Bundle();
        //Esto no tiene mucha ciencia -> Clave, valor.
        args.putInt(ID_PELICULA, idPelicula);
        fragment.setArguments(args);
        return fragment;
    }

    /*
        Aquí están disponibles ya los datos necesarios.
     */

    public PeliculaConReparto getActoresPeli() {
        return actoresPeli;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idPelicula = getArguments().getInt(ID_PELICULA);
            //cargamos los datos de la db para esa pelicula
            this.actoresPeli = AppDatabase.getDatabase(this.getContext())
                    .getInterpretePeliculaCrossRefDAO()
                    .getPeliculaConReparto(this.idPelicula);
        }
    }

    /* Al crear la vista, cargamos los valores necesarios */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Mostramos el fragmento en el contenedor
        View root= inflater.inflate(R.layout.fragment_actores, container, false);
        //cogemos el recycler
        RecyclerView rvActores = root.findViewById(R.id.reciclerViewReparto);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        rvActores.setLayoutManager(layoutManager);

        ListaInterpretesAdapter lpAdapter= new ListaInterpretesAdapter(this.actoresPeli);
        rvActores.setAdapter(lpAdapter);

        return root;
    }
}