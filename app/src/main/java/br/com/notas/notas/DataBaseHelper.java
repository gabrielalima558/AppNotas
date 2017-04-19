package br.com.notas.notas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 15251367 on 15/02/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "notas.db";
    private static final int VERSAO = 1;

    public DataBaseHelper(Context context) {super(context,NOME_BANCO, null, VERSAO);}


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table tblNotas("+
                    "_id INTEGER primary key," +
                    "nome VARCHAR(25), "+
                    "nota TEXT);");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
