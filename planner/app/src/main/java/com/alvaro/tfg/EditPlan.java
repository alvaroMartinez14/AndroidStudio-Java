package com.alvaro.tfg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.alvaro.tfg.db.crud;
import java.util.ArrayList;

public class EditPlan extends AppCompatActivity {

    //variables
    EditText nombre, desc, lugar, fecha, personas;
    boolean validado;
    ArrayList<String> lista;

    //creamos un objeto con conexion a la bd
    crud objCRUD = new crud(EditPlan.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plan);

        //ids
        nombre = findViewById(R.id.nombre);
        desc = findViewById(R.id.desc);
        lugar = findViewById(R.id.lugar);
        fecha = findViewById(R.id.fecha);
        personas = findViewById(R.id.personas);

        //extraemos la variable del otro activity a traves del intent
        String nombrePlan = getIntent().getStringExtra("nombrePlan");

        //llenamos la lista con todos los planes
        lista = objCRUD.listar(nombrePlan);

        //seteamos los datos en los editText
        nombre.setText(nombrePlan);
        desc.setText(lista.get(0));
        lugar.setText(lista.get(1));
        fecha.setText(lista.get(2));
        personas.setText(lista.get(3));

    }

    public void edit(View v) {

        if (nombre.getText().toString().isEmpty())  {
            Toast.makeText(this, "Name field empty", Toast.LENGTH_SHORT).show();
            validado = false;
        } else if (desc.getText().toString().isEmpty()) {
            Toast.makeText(this, "Description field empty", Toast.LENGTH_SHORT).show();
            validado = false;
        } else if (lugar.getText().toString().isEmpty()) {
            Toast.makeText(this, "Empty place field", Toast.LENGTH_SHORT).show();
            validado = false;
        } else if (fecha.getText().toString().equals("Date")) {
            Toast.makeText(this, "Date field empty", Toast.LENGTH_SHORT).show();
            validado = false;
        } else if (fecha.getText().toString().equals("ERROR. Date not valid")) {
            Toast.makeText(this, "Invalid date field", Toast.LENGTH_SHORT).show();
            validado = false;
        } else if (personas.getText().toString().equals("ERROR. People not valid")) {
            Toast.makeText(this, "Invalid people fieldo", Toast.LENGTH_SHORT).show();
            validado = false;
        } else {
            validado = true;
        }

        if (validado) {

            //extraemos la variable del otro activity a traves del intent
            String nombrePlan = getIntent().getStringExtra("nombrePlan");

            objCRUD.edit_plan(nombrePlan, nombre.getText().toString(), desc.getText().toString(), lugar.getText().toString(), fecha.getText().toString(), personas.getText().toString());
            Intent myPlans = new Intent(v.getContext(), com.alvaro.tfg.my_plans.class);
            startActivityForResult(myPlans, 0);
            Toast.makeText(this, "Plan successfully edited", Toast.LENGTH_SHORT).show();

        }

    }

}
