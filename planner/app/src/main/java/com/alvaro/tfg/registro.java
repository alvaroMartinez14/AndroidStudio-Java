package com.alvaro.tfg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.alvaro.tfg.db.crud;

public class registro extends AppCompatActivity {

    //variables
    EditText user, email, pass, pass2;
    boolean validado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //asigno sus ids
        user = findViewById(R.id.user);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        pass2 = findViewById(R.id.pass2);

    }

    public void new_account(View v) {

        //validar los datos
        if (user.getText().toString().isEmpty())  {
            Toast.makeText(this, "Empty user", Toast.LENGTH_SHORT).show();
            validado = false;
        } else if (email.getText().toString().isEmpty()) {
            Toast.makeText(this, "Empty mail", Toast.LENGTH_SHORT).show();
            validado = false;
        } else if (pass.getText().toString().isEmpty()) {
            Toast.makeText(this, "Empty password", Toast.LENGTH_SHORT).show();
            validado = false;
        } else if (pass2.getText().toString().isEmpty()) {
            Toast.makeText(this, "Empty confirm password", Toast.LENGTH_SHORT).show();
            validado = false;
        } else if (!email.getText().toString().contains("@gmail.com")) {
            Toast.makeText(this, "Mail format not valid", Toast.LENGTH_SHORT).show();
            validado = false;
        } else if (!pass.getText().toString().equals(pass2.getText().toString())) {
            Toast.makeText(this, "Different passwords", Toast.LENGTH_SHORT).show();
            validado = false;
        } else  {
            validado = true;
        }

        if (validado) {

            crud objCRUD = new crud(registro.this);
            long id = objCRUD.insert_user(user.getText().toString(), email.getText().toString(), pass.getText().toString());

            //si el id es mayor a 0 es que se ha insertado correctamente el usuario
            if (id > 0) {
                Toast.makeText(this, "Record saved", Toast.LENGTH_SHORT).show();
                limpiar();
            } else {
                Toast.makeText(this, "Failed to save record", Toast.LENGTH_SHORT).show();
            }

            Intent inicio = new Intent(v.getContext(), com.alvaro.tfg.MainActivity.class);  //vuelvo al login
            startActivityForResult(inicio, 0);
        }

    }

    //limpiamos los campos para que la proxima vez que se acceda al activity no queden los datos anteriores
    private void limpiar() {
        user.setText("");
        email.setText("");
        pass.setText("");
        pass2.setText("");
    }

}
