package com.example.appestoque;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ConexaoBD extends SQLiteOpenHelper {

    private static final String name = "banco.db";
    private static final int version = 1;

    public ConexaoBD (@Nullable Context context) {
        super( context, name, null, version );
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL( "CREATE TABLE tb_ListaCompras (" +
                "lcid integer PRIMARY KEY AUTOINCREMENT," +
                "nm_produto varchar(50), " +
                "qt_produto varchar(50), " +
                "qt_min_produto varchar(50), " +
                "valor varchar(50))");

        db.execSQL( "CREATE TABLE tb_Produtos (" +
                "id integer PRIMARY KEY AUTOINCREMENT," +
                "nm_produtos varchar(50), " +
                "qt_produtos varchar(50), " +
                "qt_min_produtos varchar(50), " +
                "preco varchar(50))");


    }

    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS tb_produtos";
        db.execSQL(sql);
        sql = "DROP TABLE IF EXISTS tb_listacompras";
        db.execSQL(sql);
        onCreate(db);
    }
}
