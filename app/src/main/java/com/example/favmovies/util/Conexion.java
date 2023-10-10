package com.example.favmovies.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Conexion {

    private Context aContexto;

    public Conexion(Context aContexto) {
        this.aContexto = aContexto;
    }

    public boolean compruebaConexion(){
        boolean conectado = false;
        ConnectivityManager conManager = (ConnectivityManager)
                aContexto.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = conManager.getActiveNetworkInfo();
        conectado = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return conectado;
    }
}
