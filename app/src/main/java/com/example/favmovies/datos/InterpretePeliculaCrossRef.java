package com.example.favmovies.datos;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "InterpretePeliculaCrossRef",
    primaryKeys = {"pelicula_id","interprete_id"})
public class InterpretePeliculaCrossRef implements Parcelable {

    @NonNull
    @ColumnInfo
    private int pelicula_id;
    @NonNull
    @ColumnInfo
    private int interprete_id;

    public int getPelicula_id() {
        return pelicula_id;
    }

    public void setPelicula_id(int pelicula_id) {
        this.pelicula_id = pelicula_id;
    }

    public int getInterprete_id() {
        return interprete_id;
    }

    public void setInterprete_id(int interprete_id) {
        this.interprete_id = interprete_id;
    }

    public InterpretePeliculaCrossRef(int pelicula_id, int interprete_id) {
        this.pelicula_id = pelicula_id;
        this.interprete_id = interprete_id;
    }

    protected InterpretePeliculaCrossRef(Parcel in) {
        pelicula_id = in.readInt();
        interprete_id = in.readInt();
    }

    public static final Creator<InterpretePeliculaCrossRef> CREATOR = new Creator<InterpretePeliculaCrossRef>() {
        @Override
        public InterpretePeliculaCrossRef createFromParcel(Parcel in) {
            return new InterpretePeliculaCrossRef(in);
        }

        @Override
        public InterpretePeliculaCrossRef[] newArray(int size) {
            return new InterpretePeliculaCrossRef[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(pelicula_id);
        parcel.writeInt(interprete_id);
    }
}
