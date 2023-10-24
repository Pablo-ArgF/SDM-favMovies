package com.example.favmovies.util;
public class  ImageManager {
    public static final String URL_IMAGEN_INTERPRETE = "https://image.tmdb.org/t/p/original/";

    public static String getImageCaratula(String url){
        return URL_IMAGEN_INTERPRETE + url;
    }
}
