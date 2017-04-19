package br.com.notas.notas;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdicionarActivity extends AppCompatActivity {

    EditText edit_nome_nota, edit_descricao_nota;
    Button btn_salvar;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;

        edit_nome_nota = (EditText)findViewById(R.id.edit_nome_nota);
        edit_descricao_nota = (EditText)findViewById(R.id.edit_descricao_nota);
        btn_salvar = (Button)findViewById(R.id.btn_salvar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        configDoBotao();
    }

    private void configDoBotao(){
        btn_salvar.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                inserirDadosNoBanco();

            }

        });


    }

    private void inserirDadosNoBanco() {

        SQLiteDatabase db =new DataBaseHelper(context).getReadableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", edit_nome_nota.getText().toString());
        contentValues.put("nota", edit_descricao_nota.getText().toString());

        db.insert("tblNotas", null,contentValues);

        Toast.makeText(context,"Inserido com sucesso",Toast.LENGTH_LONG );

        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);


    }

}
