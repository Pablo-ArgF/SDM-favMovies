package com.example.favmovies.modelo;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.favmovies.datos.InterpretePeliculaCrossRef;

import java.util.List;

/*
    Fíjate que no es una entidad.
 */
public class PeliculaConReparto implements Parcelable {

    /*
        @Embebbed para indicar la clase padre -> Una película.

     */

    @Embedded
    private Pelicula pelicula;



    /*

    @Relation para establecer la relación:
        parentColumn -> El campo id de la clase Pelicula (que es la clase padre)
        entityColumn -> El campo id de la clase Interprete.
        En el associateBy hacemos el Join mediante:
        @Junction:
            value = La clase que representa el JOIN.

            parentColumn = Nombre del campo que representa la id de la Pelicula en InterpretePeliculaCrossRef
                                ¡Recuerda! En este caso establecimos clase padre a Pelicula.

            entityColumn = Nombre del campo que repesenta la id del Interprete en InterpretePeliculaCrossRef


     Así los objetos de esta clase tendrán UNA película y una LISTA de intérpretes (o reparto).

     Obviamente, este proceso podrías hacerlo al revés:
        Tener un intérprete y TODAS las películas en las que aparece.
    */

    @Relation(
            parentColumn = "id",
            entityColumn = "id",
            associateBy = @Junction(
                                value = InterpretePeliculaCrossRef.class,
                                parentColumn = "pelicula_id",
                                entityColumn = "interprete_id"
                            )
    )
    private List<Interprete> reparto;

    public PeliculaConReparto() {}


    protected PeliculaConReparto(Parcel in) {
        pelicula = in.readParcelable(Pelicula.class.getClassLoader());
        reparto = in.createTypedArrayList(Interprete.CREATOR);
    }

    public static final Creator<PeliculaConReparto> CREATOR = new Creator<PeliculaConReparto>() {
        @Override
        public PeliculaConReparto createFromParcel(Parcel in) {
            return new PeliculaConReparto(in);
        }

        @Override
        public PeliculaConReparto[] newArray(int size) {
            return new PeliculaConReparto[size];
        }
    };

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public List<Interprete> getReparto(){
        return reparto;
    }

    public void setReparto(List<Interprete> reparto){
        this.reparto = reparto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeParcelable(pelicula, i);
        parcel.writeTypedList(reparto);
    }
}
