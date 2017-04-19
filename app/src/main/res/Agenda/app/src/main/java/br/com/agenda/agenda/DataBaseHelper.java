package br.com.agenda.agenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sn1041520 on 08/02/2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "agendaContatos.db";
    private static final int VERSAO = 1;

    public DataBaseHelper(Context context){
        super(context,NOME_BANCO,null, VERSAO);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table tblContato( " +
                "_id INTEGER primary key," +
                "nome TEXT," +
                "telefone TEXT," +
                "email TEXT );");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
