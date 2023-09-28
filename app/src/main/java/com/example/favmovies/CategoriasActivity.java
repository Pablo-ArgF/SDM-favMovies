package com.example.favmovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.favmovies.modelo.Categoria;

public class CategoriasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        // Recepción de datos
        Intent intent = getIntent();
        int postCategoria = intent.getIntExtra(MainActivity.POS_CATEGORIA_SELECCIONADA, 0);

        Categoria catEntrada = null;

        if(postCategoria>0){
            catEntrada = intent.getParcelableExtra(MainActivity.CATEGORIA_SELECCIONADA);

            TextView textViewCre = (TextView) findViewById(R.id.tituloCrearCategoria); //TODO posible fallo
            EditText editNameCategoria = (EditText) findViewById(R.id.editCategoryName);
            EditText editDescripcion = (EditText) findViewById(R.id.editCateogryDescription);
            // Recuperamos referencia al botón
            Button btnOK = (Button)findViewById(R.id.btnOkCategoria);
            Button btnCancel = (Button)findViewById(R.id.btnCancel);


            if(postCategoria==0)
                textViewCre.setText(R.string.NuevaCategoria);
            else {
                textViewCre.setText(R.string.modifica_categoria);
                editNameCategoria.setText(catEntrada.getNombre());
                editDescripcion.setText(catEntrada.getDescripcion());
                // no dejamos cambiar el nombre de la categoria
                editNameCategoria.setEnabled(false);
            }
            // Definir el observber para el evento click del botón guardar
            // Definimos listener
            btnOK.setOnClickListener(new View.OnClickListener(){
            // TODO comrpobar botones13:1
                @Override
                public void onClick(View view) {
                    Categoria categSalida = new Categoria(editNameCategoria.toString(), editDescripcion.toString());
                    Intent intentResultado = new Intent();
                    intentResultado.putExtra(MainActivity.CATEGORIA_MODIFICADA, categSalida);
                    setResult(RESULT_OK,intentResultado);
                    Log.i("Paso por aquí", "Voy a por el finish");
                    finish();
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setResult(RESULT_CANCELED);
                    finish();
                }
            });
        }
    }
}