package com.alvaro.tfg;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.alvaro.tfg.db.conexion;
import com.alvaro.tfg.db.crud;
import com.alvaro.tfg.entidades.Usuarios;

public class MainActivity extends AppCompatActivity {

    //variables
    EditText user, pass;
    boolean validado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ids
        user = findViewById(R.id.user);
        pass = findViewById(R.id.email);

        //llamamos a la conexion
        conexion conn = new conexion(MainActivity.this);
        SQLiteDatabase db = conn.getWritableDatabase();

        if (db == null) Toast.makeText(this, "DB ERROR", Toast.LENGTH_SHORT).show();

    }

    //boton login
    public void go(View v) {

        crud objCRUD = new crud(MainActivity.this);
        Usuarios usuario = new Usuarios();

        //validamos los datos
        if (user.getText().toString().isEmpty() || pass.getText().toString().isEmpty() || !objCRUD.buscarLogin(usuario, user.getText().toString(), pass.getText().toString()))  {
            Toast.makeText(this, "Incorrect login", Toast.LENGTH_SHORT).show();
            validado = false;
        } else  {
            Toast.makeText(this, "Correct login", Toast.LENGTH_SHORT).show();
            validado = true;
        }

        if (validado) {
            Intent empezar = new Intent(v.getContext(), com.alvaro.tfg.principal.class);
            startActivityForResult(empezar, 0);
        }

    }

    //boton registrarse
    public void registrarme(View v) {
        Intent reg = new Intent(v.getContext(), com.alvaro.tfg.registro.class);
        startActivityForResult(reg, 0);
    }

}
