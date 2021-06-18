package com.alvaro.tfg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.alvaro.tfg.db.crud;
import java.util.ArrayList;

public class my_plans extends AppCompatActivity {

    //variables
    ListView lv;
    ArrayList<String> lista;
    ArrayAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plans);

        //ids
        lv = findViewById(R.id.lv);

        //creamos un objeto con conexion a la bd
        crud objCRUD = new crud(my_plans.this);

        //llenamos la lista con todos los planes
        lista = objCRUD.listar(null);

        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, lista);

        lv.setAdapter(adaptador);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String nombrePlan = lista.get(position);

                Intent other = new Intent(view.getContext(), com.alvaro.tfg.info_plans.class);

                //pasar un dato de un activity a otro
                    other.putExtra("nombrePlan", nombrePlan);

                startActivityForResult(other, 0);

            }
        });

    }

    public void anotherPlan(View v) {
        Intent other = new Intent(v.getContext(), com.alvaro.tfg.CrearPlan.class);
        startActivityForResult(other, 0);
    }

}
