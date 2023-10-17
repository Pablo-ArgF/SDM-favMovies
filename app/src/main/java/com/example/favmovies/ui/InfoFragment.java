package com.example.favmovies.ui;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.favmovies.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InfoFragment extends Fragment {


    private static final String FECHA_PELI="fecha";
    private static final String DURACION_PELI="duracion";
    private static final String CARATULA_PELI="Caratula";
    private String duracionPeli;
    private String fechaPeli;
    private String urlCaratulaPeli;


    /*

        Esto es un FactoryMethod.
        Los datos están siendo enviados ANTES del onCreate.
        El Bundle permanece cuando se tiene que recrear.
     */
    public static InfoFragment newInstance(String duracionPeli, String fechaPeli, String urlCaratulaPeli) {
        InfoFragment fragment = new InfoFragment();
        Bundle args = new Bundle();
        //Esto no tiene mucha ciencia -> Clave, valor.
        args.putString(FECHA_PELI, fechaPeli);
        args.putString(DURACION_PELI, duracionPeli);
        args.putString(CARATULA_PELI, urlCaratulaPeli);
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
            fechaPeli = getArguments().getString(FECHA_PELI);
            duracionPeli = getArguments().getString(DURACION_PELI);
            urlCaratulaPeli = getArguments().getString(CARATULA_PELI);

        }
    }

    /* Al crear la vista, cargamos los valores necesarios */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Mostramos el fragmento en el contenedor
        View root= inflater.inflate(R.layout.fragment_info, container, false);
        TextView tvFecha = root.findViewById(R.id.text_fecha);
        TextView tvDuracion = root.findViewById(R.id.text_fecha);
        ImageView ivCaratula = root.findViewById(R.id.img_caratula);
        tvFecha.setText(fechaPeli);
        tvDuracion.setText(duracionPeli);
        Picasso.get()
                .load(urlCaratulaPeli).into(ivCaratula);

        return root;
    }
}