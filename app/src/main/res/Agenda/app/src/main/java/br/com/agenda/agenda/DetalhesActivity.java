package br.com.agenda.agenda;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetalhesActivity extends AppCompatActivity {

    Integer idContato;
    TextView txt_nome_contato;
    TextView txt_telefone_contato;
    TextView txt_email_contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_nome_contato = (TextView)findViewById(R.id.txt_nome_contato);
        txt_telefone_contato = (TextView)findViewById(R.id.txt_telefone_contato);
        txt_email_contato = (TextView)findViewById(R.id.txt_email_contato);

        Intent intent = getIntent();

        /*se o intent ffor nulo*/
        if(intent !=null){

            idContato = intent.getIntExtra("idContato",0);

            SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase();

            Cursor cursor= db.rawQuery("SELECT * FROM tblContato WHERE _id = ?;", new String[]{idContato.toString()});//prestar ateção até nos espaços dessa linha


            if(cursor.getCount()>0){

                cursor.moveToFirst();//mover cursor

                String nomeContato = cursor.getString(1);//coluna nome
                String telefoneContato = cursor.getString(2);// coluna telefone
                String emailContato = cursor.getString(3);// coluna email

                txt_nome_contato.setText(nomeContato);
                txt_telefone_contato.setText(telefoneContato);
                txt_email_contato.setText(emailContato);

                cursor.close();//fechar cursor



            }


        }
    }

    //criação do menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detalhes,menu);
        return true;


    }
    //responder aos cliques do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){

            //editar
            case R.id.menu_editar:
                Intent intent = new Intent(this,EditarActivity.class);

                intent.putExtra("idContato", idContato );
                startActivity(intent);

                break;


            //excluir
            case R.id.menu_excluir:

                confirmarExcluir();
                break;
        }

        return super.onOptionsItemSelected(item);
    }



    public void confirmarExcluir(){

        //Alerta
        new AlertDialog.Builder(this)
                .setTitle("Excluir")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Deseja excluir este item?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        excluirContato();
                    }
                })
                .setNegativeButton("Não", null)
                .show();




    }


    public void excluirContato(){

        SQLiteDatabase db = new DataBaseHelper(this).getWritableDatabase();

        db.delete("tblContato","_id = ?",new String[]{idContato.toString()});

        Toast.makeText(this,"Excluido com sussesso", Toast.LENGTH_LONG).show();

        startActivity(new Intent(this,MainActivity.class));

    }
}
