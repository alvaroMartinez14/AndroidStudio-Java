package com.alvaro.tfg.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.Nullable;
import com.alvaro.tfg.entidades.Planes;
import com.alvaro.tfg.entidades.Usuarios;
import java.util.ArrayList;

public class crud extends conexion {

    Context context;

    //contructor
    public crud(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    //metodos
    public long insert_user(String user, String email, String pass) {

        long id = 0;

        try {
            //se llama a la conexion y se abre en modo escritura
            conexion conn = new conexion(context);
            SQLiteDatabase db = conn.getWritableDatabase();

            //se añaden los valores a la tabla
            ContentValues values = new ContentValues();
            values.put("user", user);
            values.put("email", email);
            values.put("pass", pass);

            id = db.insert("t_usuarios", null, values);  //devuelve el id donde se ha insertado


        } catch(Exception e) {
            e.printStackTrace();
        }

        return id;

    }

    public long insert_plan(String nombre, String descripcion, String lugar, String fecha, String personas) {

        long id = 0;

        try {
            conexion conn = new conexion(context);
            SQLiteDatabase db = conn.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("descripcion", descripcion);
            values.put("lugar", lugar);
            values.put("fecha", fecha);
            values.put("personas", personas);

            id = db.insert("t_planes", null, values);  //devuelve el id donde se ha insertado


        } catch(Exception e) {
            e.printStackTrace();
        }

        return id;

    }

    public void delete_plan(String nombre) {

        conexion conn = new conexion(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        if (db != null) {
            db.execSQL("DELETE FROM t_planes WHERE nombre='"+nombre+"'");
            db.close();
        }

    }

    public void edit_plan(String nombrePlan, String nombre, String desc, String lugar, String fecha, String pers) {

        conexion conn = new conexion(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        if (db != null) {
            db.execSQL("UPDATE t_planes SET nombre='"+nombre+"', descripcion='"+desc+"', lugar='"+lugar+"',fecha='"+fecha+"', personas='"+pers+"' WHERE nombre='"+nombrePlan+"'");
            db.close();
        }

    }

    //este metodo se utiliza en el login. Se hace la query para buscar dentro de la tabla a un usuario determinado siempre y cuando
    //el user y la contraseña sea la introducida. Si es asi, el metodo devuekve un booleano indicando que la busqueda se ha realizado correctamente
    //y que existe ese usuario con esa contraseña
    public boolean buscarLogin(Usuarios usuario, String user, String pass) {

        conexion conn = new conexion(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        Cursor c = null;
        boolean ok = false;

        c = db.rawQuery("SELECT * FROM t_usuarios WHERE user='"+user+"'AND pass='"+pass+"'", null);
        if (c.getCount() != 0) ok = true;

        return ok;
    }

    //este metodo devuelve una cadena de texto donde se guarda un texto personalizado para compartir el plan
    public String compartirPlan(Planes plan, String nombre) {

        conexion conn = new conexion(context);
        SQLiteDatabase db = conn.getWritableDatabase();

        Cursor c = null;
        String cadena = "Te propongo un plan ! \n";

        c = db.rawQuery("SELECT nombre, descripcion, lugar, fecha, personas FROM t_planes WHERE nombre='"+nombre+"'", null);        //la consulta se hace por nombre

        if (c.moveToFirst()) {
            do {
                cadena = "\tPlan: " + c.getString(0);
                cadena = cadena + "\n ¿Qué vamos a hacer? ->" + c.getString(1);
                cadena = cadena + ".\n¿Dónde? -> " + c.getString(2);
                cadena = cadena + "\n¿Qué día? ->" + c.getString(3);
                cadena = cadena + "\nPersonas apuntadas ->" + c.getString(4) + "\n\n ¿Te apuntas? :)";
            } while (c.moveToNext());
        }

        return cadena;
    }

    //este metodo esta modificado para reutilizarlo en dos sitios distintos
        //por un lado si se le pasa un nombre de plan se hace la consulta de ese plan en concreto para mostrar sus datos especificos y devuelve el array con los datos
        //por otro lado, si se le pasa a nulo, es que se quieren listar todos los planes que existan, devolviendo un array con los nombres de todos los planes
    public ArrayList listar(String nombre) {

        conexion conn = new conexion(context);
        SQLiteDatabase db = conn.getWritableDatabase();
        ArrayList<String> lista = new ArrayList<>();
        Cursor c = null;

        if (nombre != null) {
            c = db.rawQuery("SELECT nombre, descripcion, lugar, fecha, personas FROM t_planes WHERE nombre='"+nombre+"'", null);

            if (c.moveToFirst()) {
                do {
                    lista.add(c.getString(1));
                    lista.add(c.getString(2));
                    lista.add(c.getString(3));
                    lista.add(c.getString(4));
                } while (c.moveToNext());
            }

            return lista;

        } else {
            c = db.rawQuery("SELECT * FROM t_planes", null);

            if (c.moveToFirst()) {
                do {
                    lista.add(c.getString(1));
                } while (c.moveToNext());
            }

            return lista;
        }

    }

}
