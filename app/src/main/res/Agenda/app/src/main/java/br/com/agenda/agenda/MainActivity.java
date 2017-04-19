package br.com.agenda.agenda;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //SEM orientação a objeto

    Context context;

    ListView list_view_contatos;

    List<String> listaContatos = new ArrayList<>();
    List<Integer>listaIdsContatos = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;

        list_view_contatos= (ListView) findViewById(R.id.list_view_contatos);

        /*funções que estão lá em baixo*/
        configurarBotaoFlutuante();
        buscarDadosNoBanco();
        preencherAdapter();


        /*clicar em opções da lista e ir pra tela de detalhes
         */
        list_view_contatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Integer idContato = listaIdsContatos.get(position);//acessando a lista naquela posição trará o idContato

                Intent intent = new Intent(context, DetalhesActivity.class);

                intent.putExtra("idContato",idContato);//Interger
                startActivity(intent);
            }
        });
    }

    private void preencherAdapter() {
        ArrayAdapter<String> arrayAdapter;
        arrayAdapter = new ArrayAdapter<String>(
                context,
                android.R.layout.simple_list_item_1,
                listaContatos
        );

        list_view_contatos.setAdapter(arrayAdapter);
    }

    private void buscarDadosNoBanco() {
        //Fazer select no banco e preencher a lista

        SQLiteDatabase db = new
                DataBaseHelper(context).getReadableDatabase();


        //Fazer o comando select
        String comandoSql = "SELECT * FROM tblContato;";
        Cursor cursor = db.rawQuery(comandoSql, null);

        //verificar se veio algum registro
        if(cursor.getCount() > 0 ){
            cursor.moveToFirst();

            for(int i =0 ; i < cursor.getCount(); i++){


                listaIdsContatos.add(cursor.getInt(0));//acessando a coluna id
                listaContatos.add( cursor.getString(1));//acessando a coluna do banco que nessa caso á a um

                cursor.moveToNext();
            }
        }
        cursor.close();
        //Termino do select
    }

    private void configurarBotaoFlutuante() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new
                        Intent(context, NovoContatoActivity.class);

                startActivity(intent);
            }
        });
    }

}
