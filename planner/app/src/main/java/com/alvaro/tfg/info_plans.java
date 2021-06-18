package com.alvaro.tfg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.alvaro.tfg.db.crud;
import java.util.ArrayList;

public class info_plans extends AppCompatActivity {

    //variables
    TextView res;
    ArrayList<String> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_plans);

        //ids
        res = findViewById(R.id.res);

        String nombrePlan = getIntent().getStringExtra("nombrePlan");
        crud objCRUD = new crud(info_plans.this);

        //llenamos la lista con todos los planes
        lista = objCRUD.listar(nombrePlan);

        //seteamos todos los datos
        res.setText("Plan: "+nombrePlan+"\nDescription: "+lista.get(0)+"\nPlace: "+lista.get(1)+"\nDate: "+lista.get(2)+"\nTarget people: "+lista.get(3));

    }

    public void edit_plan(View v) {

        String nombrePlan = getIntent().getStringExtra("nombrePlan");
        Intent editar = new Intent(v.getContext(), com.alvaro.tfg.EditPlan.class);
        editar.putExtra("nombrePlan", nombrePlan);
        startActivityForResult(editar, 0);

    }

    public void delete_plan(View v) {

        String nombrePlan = getIntent().getStringExtra("nombrePlan");
        crud objCRUD = new crud(info_plans.this);
        objCRUD.delete_plan(nombrePlan);

        Intent listado = new Intent(v.getContext(), com.alvaro.tfg.my_plans.class);
        startActivityForResult(listado, 0);

        Toast.makeText(this, "The plan was deleted from your list", Toast.LENGTH_SHORT).show();

    }

}
