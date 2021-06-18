package com.alvaro.tfg;

import androidx.appcompat.app.AppCompatActivity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import com.alvaro.tfg.db.crud;
import com.alvaro.tfg.entidades.Planes;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CrearPlan extends AppCompatActivity {

    //variables
    EditText nombre, desc, lugar, fecha, personas;
    DatePickerDialog dpd;
    Calendar c;
    Date date;
    long miliseg;
    boolean validado;
    ArrayList<String> lista;

    //creamos un objeto con conexion a la bd
    crud objCRUD = new crud(CrearPlan.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_plan);

        //ids
        nombre = findViewById(R.id.nombre);
        desc = findViewById(R.id.desc);
        lugar = findViewById(R.id.lugar);
        personas = findViewById(R.id.personas);

        //abrir el calendario
        fecha = findViewById(R.id.fecha);
        fecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c = Calendar.getInstance();

                dpd = new DatePickerDialog(CrearPlan.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {

                        if (year < c.get(Calendar.YEAR) || month < c.get(Calendar.MONTH)) {
                            fecha.setText("ERROR. Fecha pasada");
                        } else if (year == c.get(Calendar.YEAR) && month == c.get(Calendar.MONTH) && day < c.get(Calendar.DAY_OF_MONTH)) {
                            fecha.setText("ERROR. Fecha pasada");
                        } else {
                            month = month+1;
                            fecha.setText(day + " / " + month + " / " + year);
                            date = new Date(year-1900, month-1, day);
                            miliseg = date.getTime();
                        }
                    }
                },
                    c.get(Calendar.YEAR),
                    c.get(Calendar.MONTH),
                    c.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show();
            }
        });

    }

    public void agregar_plan(View v) {

        if (validar()) {

            long id = objCRUD.insert_plan(nombre.getText().toString(), desc.getText().toString(), lugar.getText().toString(), fecha.getText().toString(), personas.getText().toString());

            //si el id es mayor a 0 significa que se ha insertado bien el plan
            if (id > 0) {
                Toast.makeText(this, "Redirecting...", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Redirecting error", Toast.LENGTH_SHORT).show();
            }

            //guardar el plan creado para compartirlo posteriormente
            Planes plan = new Planes();
            String datosCompartir = objCRUD.compartirPlan(plan, nombre.getText().toString());

            String title = nombre.getText().toString();
            String descr = desc.getText().toString();
            String loc = lugar.getText().toString();

            //agregar al calendario
            Intent calendario = new Intent(Intent.ACTION_INSERT);
            calendario.setData(CalendarContract.Events.CONTENT_URI);
            calendario.putExtra(CalendarContract.Events.TITLE, title);
            calendario.putExtra(CalendarContract.Events.DESCRIPTION, descr);
            calendario.putExtra(CalendarContract.Events.EVENT_LOCATION, loc);
            calendario.putExtra(CalendarContract.Events.ALL_DAY, true);
            calendario.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, miliseg);
            calendario.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, miliseg+86400000);

            Intent intent = new Intent(v.getContext(), com.alvaro.tfg.compartir.class);
            intent.putExtra("datosCompartir", datosCompartir);  //pasar un dato de un activity a otro
            startActivityForResult(intent, 0);

            startActivity(calendario);

        }

    }

    public void share_plan(View v) {

        if (validar()) {

            long id = objCRUD.insert_plan(nombre.getText().toString(), desc.getText().toString(), lugar.getText().toString(), fecha.getText().toString(), personas.getText().toString());

            if (id > 0) {
                Toast.makeText(this, "Saved plan", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error saving plan", Toast.LENGTH_SHORT).show();
            }

            //guardar el plan creado para compartirlo posteriormente
            Planes plan = new Planes();
            String datosCompartir = objCRUD.compartirPlan(plan, nombre.getText().toString());

            Intent intent = new Intent(v.getContext(), com.alvaro.tfg.compartir.class);
            intent.putExtra("datosCompartir", datosCompartir);  //pasar un dato de un activity a otro
            startActivityForResult(intent, 0);

        }

    }

    //metodo validar campos
    public boolean validar() {

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

        return validado;
    }

}
