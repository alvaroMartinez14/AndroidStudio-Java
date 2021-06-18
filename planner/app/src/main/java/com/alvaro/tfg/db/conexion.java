package com.alvaro.tfg.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class conexion extends SQLiteOpenHelper {

    //nombre de nuestra bd
    private static final String DATABASE_NAME = "planner.db";

    //constructor
    public conexion(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //creamos tabla usuarios con sus campos
        db.execSQL("CREATE TABLE t_usuarios (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "user TEXT NOT NULL," +
            "email TEXT NOT NULL," +
            "pass TEXT NOT NULL)");

        //creamos tabla planes con sus campos
        db.execSQL("CREATE TABLE t_planes (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "descripcion TEXT NOT NULL," +
                "lugar TEXT NOT NULL," +
                "fecha LONG NOT NULL," +
                "personas TEXT NOT NULL)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //cada vez que se actualiza la bd se borran las tablas y se vuelve a crear
        db.execSQL("DROP TABLE t_usuarios AND t_planes");
        onCreate(db);

    }
}
