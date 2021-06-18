package com.alvaro.tfg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class compartir extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compartir);

    }

    //boton compartir
    public void share_plan(View v) {

        //recuperar dato del otro activity a traves de un intent
        String datosCompartir = getIntent().getStringExtra("datosCompartir");

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, datosCompartir);
        sendIntent.setType("text/plain");

        Intent share = new Intent(v.getContext(), com.alvaro.tfg.listo.class);
        startActivityForResult(share, 0);

        startActivity(sendIntent);

    }

    //boton crear otro plan
    public void anotherPlan(View v) {
        Intent other = new Intent(v.getContext(), com.alvaro.tfg.CrearPlan.class);
        startActivityForResult(other, 0);
    }

    //boton salir
    public void planes(View v) {
        Intent find = new Intent(v.getContext(), com.alvaro.tfg.my_plans.class);
        startActivityForResult(find, 0);
    }

}
