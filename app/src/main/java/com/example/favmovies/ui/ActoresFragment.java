package com.example.favmovies.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.favmovies.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActoresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActoresFragment extends Fragment {

    private static final String ACTORES_PELI="Argumento";
    private String actoresPeli;


    /*

        Esto es un FactoryMethod.
        Los datos están siendo enviados ANTES del onCreate.
        El Bundle permanece cuando se tiene que recrear.
     */
    public static ActoresFragment newInstance(String actoresPeli) {
        ActoresFragment fragment = new ActoresFragment();
        Bundle args = new Bundle();
        //Esto no tiene mucha ciencia -> Clave, valor.
        args.putString(ACTORES_PELI, actoresPeli);
        fragment.setArguments(args);
        return fragment;
    }

    /*
        Aquí están disponibles ya los datos necesarios.
     */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            actoresPeli = getArguments().getString(ACTORES_PELI);

        }
    }

    /* Al crear la vista, cargamos los valores necesarios */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Mostramos el fragmento en el contenedor
        View root= inflater.inflate(R.layout.fragment_actores, container, false);
        TextView tvArgumento = root.findViewById(R.id.text_actores);
        tvArgumento.setText(actoresPeli);
        return root;
    }
}