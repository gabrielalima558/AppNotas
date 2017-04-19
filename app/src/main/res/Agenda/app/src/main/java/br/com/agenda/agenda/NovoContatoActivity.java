package br.com.agenda.agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NovoContatoActivity extends AppCompatActivity {

    Button btn_salvar;
    EditText edit_nome_contato, edit_telefone_contato,edit_email_contato;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_contato);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        edit_nome_contato = (EditText) findViewById(R.id.edit_nome_contato);
        edit_telefone_contato = (EditText) findViewById(R.id.edit_telefone_contato);
        edit_email_contato = (EditText) findViewById(R.id.edit_email_contato);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        configurarBotaoSalvar();

    }

    private void configurarBotaoSalvar() {
        btn_salvar = (Button) findViewById(R.id.btn_salvar);

        btn_salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inserirDadosBanco();
            }
        });
    }

    private void inserirDadosBanco() {
        //Banco de dados de escrita
        SQLiteDatabase db = new
                DataBaseHelper(context).getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("nome", edit_nome_contato.getText().toString());
        contentValues.put("telefone", edit_telefone_contato.getText().toString());
        contentValues.put("email", edit_email_contato.getText().toString());

        db.insert("tblContato", null , contentValues);

        Toast.makeText(context,"Salvo com sucesso",
                Toast.LENGTH_SHORT).show();
    }

}
