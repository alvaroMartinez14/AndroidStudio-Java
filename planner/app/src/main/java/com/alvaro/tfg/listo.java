package com.alvaro.tfg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class listo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listo);

        //Publicidad
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public void my_plans (View v) {
        Intent find = new Intent(v.getContext(), com.alvaro.tfg.my_plans.class);
        startActivityForResult(find, 0);
    }


    public void new_plan (View v) {
        Intent other = new Intent(v.getContext(), com.alvaro.tfg.CrearPlan.class);
        startActivityForResult(other, 0);
    }

    public void bye(View v) {
        Toast.makeText(this, "Closing session", Toast.LENGTH_LONG).show();
        Intent salir = new Intent(v.getContext(), com.alvaro.tfg.MainActivity.class);
        startActivityForResult(salir, 0);
    }

}
