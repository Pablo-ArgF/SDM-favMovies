package com.example.favmovies.datos;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.example.favmovies.modelo.Interprete;
import com.example.favmovies.modelo.Pelicula;



/*
    Room trabaja con migraciones. Cada vez que añadamos o modifiquemos una entidad
    es necesario incrementar la versión (número entero) y generar una migración.
    NO lo vamos a gestionar, pero tienes info aquí:
        https://developer.android.com/training/data-storage/room/migrating-db-versions?hl=es-419

    Lo que haremos será desinstalar la app cuando hagamos modificaciones.

    Fíjate que ademas indicamos las entidades anotadas con @Entity.
    Por ahora, solamente la clase Pelicula.

 */
@Database(version= 2, entities = {Pelicula.class, Interprete.class,InterpretePeliculaCrossRef.class})
public abstract class AppDatabase extends RoomDatabase {

    /*Aquí iremos añadiendo los DAO */
    public abstract PeliculaDAO getPeliculaDAO();
    public abstract InterpreteDAO getInterpreteDAO();
    public abstract InterpretePeliculaCrossRefDAO getInterpretePeliculaCrossRefDAO();


    public static final String DB_NOMBRE = "peliculas.db";

    private static AppDatabase db;

    // Singleton
    public static AppDatabase getDatabase(Context applicationContext) {
        if (db == null) {
            /*
                allowMainThreadQueries() implica que utilizaremos el hilo principal.
                Esto es un crimen (bloqueamos la interfaz y demás problemas).
                Lo trabajaremos la semana que viene con Kotlin.
             */
            db = Room.databaseBuilder(applicationContext, AppDatabase.class, DB_NOMBRE)
                    .allowMainThreadQueries()
                    .build();
        }
        return db;
    }



}
