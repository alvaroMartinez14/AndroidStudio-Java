package com.alvaro.tfg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        //Publicidad
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }

    public void new_plan(View v) {
        Intent crear = new Intent(v.getContext(), com.alvaro.tfg.CrearPlan.class);
        startActivityForResult(crear, 0);
    }

    public void find_plan(View v) {
        Intent find = new Intent(v.getContext(), com.alvaro.tfg.my_plans.class);
        startActivityForResult(find, 0);
    }

    public void bye(View v) {
        Toast.makeText(this, "Closing session", Toast.LENGTH_LONG).show();
        Intent salir = new Intent(v.getContext(), com.alvaro.tfg.MainActivity.class);
        startActivityForResult(salir, 0);
    }

}
