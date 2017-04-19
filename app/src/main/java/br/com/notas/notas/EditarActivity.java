package br.com.notas.notas;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditarActivity extends AppCompatActivity {

    Integer idNota;
    EditText edit_nome,edit_nota;
    Button btn_salvar;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        edit_nome = (EditText)findViewById(R.id.edit_nome);
        edit_nota = (EditText)findViewById(R.id.edit_nota);

        Intent intent = getIntent();

        if(intent != null){

            idNota = intent.getIntExtra("idNota", 0);

            BuscarDados();
        }


        btn_salvar = (Button)findViewById(R.id.btn_salvar);
        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEdit();

                Intent intent = new Intent(context,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void saveEdit(){


        SQLiteDatabase db = new DataBaseHelper(this).getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome",edit_nome.getText().toString());
        valores.put("nota",edit_nota.getText().toString());

        db.update("tblNotas",valores,"_id = ?",new String[]
                {idNota.toString()});
    }
    private void BuscarDados(){
        SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tblNotas WHERE _id = ?;",new String[]{idNota.toString()});

        if (cursor.getCount()>0){

            cursor.moveToFirst();

            String nomeNome = cursor.getString(1);
            String nomeNota = cursor.getString(2);

            edit_nome.setText(nomeNome);
            edit_nota.setText(nomeNota);

            cursor.close();


        }
    }
}
