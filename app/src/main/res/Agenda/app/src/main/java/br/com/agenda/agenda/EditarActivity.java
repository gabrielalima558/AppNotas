package br.com.agenda.agenda;

import android.content.ContentValues;
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
import android.widget.Toast;

public class EditarActivity extends AppCompatActivity {

    Integer idContato;
    EditText edit_nome_contato;
    EditText edit_telefone_contato;
    EditText edit_email_contato;
    Button btn_salvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edit_nome_contato = (EditText) findViewById(R.id.edit_nome_contato);
        edit_telefone_contato = (EditText) findViewById(R.id.edit_telefone_contato);
        edit_email_contato = (EditText) findViewById(R.id.edit_email_contato);


        Intent intent = getIntent();

        if (intent != null) {

            idContato = intent.getIntExtra("idContato", 0);

            buscarContatoBanco();
        }

        btn_salvar = (Button) findViewById(R.id.btn_salvar);
        btn_salvar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                salvarEdicao();
            }


        });

    }

    public void salvarEdicao() {
        SQLiteDatabase db = new DataBaseHelper(this).getWritableDatabase();


        ContentValues valores = new ContentValues();
        valores.put("Nome", edit_nome_contato.getText().toString());
        valores.put("Email", edit_email_contato.getText().toString());
        valores.put("Telefone", edit_telefone_contato.getText().toString());

        db.update("tblContato", valores,"_id = ?", new String[]{
                idContato.toString()
        });
        Toast.makeText(this, "Atualizado com sucesso!", Toast.LENGTH_SHORT)
                .show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);

    }

    private void buscarContatoBanco() {
        SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM tblContato WHERE _id = ?;", new String[]{idContato.toString()});//prestar ateção até nos espaços dessa linha


        if (cursor.getCount() > 0) {

            cursor.moveToFirst();//mover cursor


            String nomeContato = cursor.getString(1);//coluna nome
            String telefoneContato = cursor.getString(2);// coluna telefone
            String emailContato = cursor.getString(3);// coluna email

            edit_nome_contato.setText(nomeContato);
            edit_telefone_contato.setText(telefoneContato);
            edit_email_contato.setText(emailContato);


            cursor.close();//fechar cursor


            }
        }
    }
