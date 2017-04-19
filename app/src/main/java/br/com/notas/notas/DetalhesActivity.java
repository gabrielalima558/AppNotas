package br.com.notas.notas;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.DialogPreference;
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


    Integer idNota;
    TextView txt_nome_detalhes, txt_nota_detalhes;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        txt_nome_detalhes = (TextView) findViewById(R.id.txt_nome_detalhes);
        txt_nota_detalhes = (TextView) findViewById(R.id.txt_nota_detalhes);

        Intent intent = getIntent();

        if (intent != null) {

            idNota = intent.getIntExtra("idNota", 0);
            SQLiteDatabase db = new DataBaseHelper(this).getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM tblNotas WHERE _id =?; ", new String[]{idNota.toString()});

            if (cursor.getCount() > 0) {

                cursor.moveToFirst();

                String nomeNota = cursor.getString(1);
                String notaNota = cursor.getString(2);

                txt_nome_detalhes.setText(nomeNota);
                txt_nota_detalhes.setText(notaNota);


                cursor.close();


            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detalhes,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_editar:
                Intent intent = new Intent(this,EditarActivity.class);
                intent.putExtra("idNota",idNota);
                startActivity(intent);

                break;

            case R.id.menu_excluir:

                ConfirmarExcluir();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

   public void ConfirmarExcluir(){

       new AlertDialog.Builder(this)
               .setTitle("Excluir")
               .setIcon(android.R.drawable.ic_dialog_alert)
               .setMessage("Deseja excluir este item?")
               .setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {

                           ExcluirNota();
                       }})
                .setNegativeButton("NÃO",null)
                .show();


   }
    public void ExcluirNota(){

        SQLiteDatabase db = new DataBaseHelper(this).getWritableDatabase();
        db.delete("tblNotas","_id = ?", new String[]{idNota.toString()});
        Toast.makeText(this,"Excluido com sucesso",Toast.LENGTH_LONG).show();
        startActivity(new Intent(this,MainActivity.class));

    }

}
