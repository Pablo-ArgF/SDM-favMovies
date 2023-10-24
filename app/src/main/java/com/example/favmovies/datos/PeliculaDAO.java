package com.example.favmovies.datos;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.favmovies.modelo.Pelicula;


import java.util.List;


/*
    Al utilizar @Dao, Room generará el código necesario en tiempo de compilación
    Puede ser una interface o una clase abstracta (casos más complejos).
 */
@Dao
public interface PeliculaDAO {

    /*
        Más info sobre anotaciones en:
            https://developer.android.com/training/data-storage/room/accessing-data?hl=es-419
     */


    /*
        @Insert nos permite serializar un objeto.
        El retorno puede ser int (devolverá la id generada).

        El onConflict indica cómo proceder en caso de encontrarse con claves primarias repetidas.
        En este claso, indicamos que ignore la inserción.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void add(Pelicula pelicula);


    /*
        @Update actualiza los datos en la BD para las claves primarias que coincidan.
                (Piensa que puede recibir más de un objeto).
        El retorno puede ser int (número de filas actualizadas.
     */
    @Update
    void update(Pelicula pelicula);




    /*
       @Delete borra cuando coinciden las claves primarias.
       El retorno puede ser int (número de filas eliminadas).

     */
    @Delete
    void delete(Pelicula Pelicula);



    /*
        @Query nos permite crear una consulta.
        Fíjate en el segundo caso,
            findById recibe el párametro peliculaId
            y lo utiliza mediante (:nombre_parametro) en la consulta.
     */

    @Query("SELECT * FROM peliculas")
    List<Pelicula> getAll();


    @Query("SELECT * FROM peliculas WHERE id = (:peliculaId)")
    Pelicula findById(int peliculaId);

    @Query("SELECT * FROM peliculas where categoria_nombre = (:categoriaNombre)")
    List<Pelicula> findByCategoriaNombre(String categoriaNombre);

/*

    @Transaction
    @Query("SELECT * FROM peliculas WHERE id = (:peliculaId) LIMIT 1")
    public PeliculaConReparto getPeliculaConReparto(int peliculaId);*/

}
