package br.com.notas.notas;

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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ListView lst_notas;
    List<Integer>lstIdNotas = new ArrayList<>();
    List<String>lstNotas = new ArrayList<>();
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;


        lst_notas = (ListView)findViewById(R.id.lst_notas);


        Botao();


        lst_notas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Integer idNota = lstIdNotas.get(position);

                Intent intent = new Intent(context, DetalhesActivity.class);

                intent.putExtra("idNota",idNota);
                startActivity(intent);
            }
        });
    }


    @Override
    //Criei esse método para aquele botão do próprio aparelho para voltar a tela e carregar as alterações feitas// prestar atenção nas listas para não ocorrer a repetição
    protected void onResume() {
        super.onResume();
        BuscaDadosPeloBanco();//chamar essas funções aqui ao inés de ser no onCreate
        preencherDados();

    }

    private void preencherDados(){
        ArrayAdapter<String>arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_expandable_list_item_1,
                lstNotas
        );


        lst_notas.setAdapter(arrayAdapter);
    }

    private void BuscaDadosPeloBanco(){

        //limpar listas para não ocorrer a repetição delas na tela usando o .clear
        lstIdNotas.clear();
        lstNotas.clear();
        SQLiteDatabase db = new DataBaseHelper(context).getReadableDatabase();

        String sql= "SELECT * FROM tblNotas;";
        Cursor cursor = db.rawQuery(sql, null);

        if(cursor.getCount() > 0){

            cursor.moveToFirst();

            for (int i =0; i < cursor.getCount(); ++i ){

                lstIdNotas.add(cursor.getInt(0));
                lstNotas.add(cursor.getString(1));

                cursor.moveToNext();
            }
        }
        cursor.close();
    }

  private void Botao(){
      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
      fab.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              Intent intent = new Intent(context, AdicionarActivity.class);
              startActivity(intent);
          }
      });



  }

}
