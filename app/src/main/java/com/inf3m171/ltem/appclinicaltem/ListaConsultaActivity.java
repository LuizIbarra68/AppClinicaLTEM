package com.inf3m171.ltem.appclinicaltem;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.inf3m171.ltem.appclinicaltem.model.Consulta;

import java.util.ArrayList;
import java.util.List;

public class ListaConsultaActivity extends AppCompatActivity {

    private ListView lvConsulta;
    private List<Consulta> listaDeConsultas;

    private ArrayAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private Query queryRef;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_consulta);

        lvConsulta =  (ListView) findViewById(R.id.lvConsulta);
        listaDeConsultas = new ArrayList<>();
        adapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, listaDeConsultas);
        lvConsulta.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        lvConsulta.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                darOpcoes(i);

                return true;
            }
        });

        
    }


    private void darOpcoes(final int posicao ){
        AlertDialog.Builder alerta = new AlertDialog.Builder(ListaConsultaActivity.this);
        alerta.setTitle("Atenção");
        alerta.setIcon(android.R.drawable.ic_dialog_alert);
        alerta.setMessage("Confirma a remarcacao da consulta" + listaDeConsultas.get(posicao).getNome() + "?");
        alerta.setNeutralButton("Remarcar", null);
        alerta.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
    //              listaDeConsultas.remove(posicao);
     //             adapter.notifyDataSetChanged();




            }
        });



    }


    @Override
    protected void onStart() {
        super.onStart();
        queryRef = reference.child("Consulta").orderByChild("nome");

        listaDeConsultas.clear();

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Consulta consulta = new Consulta();
                consulta.setId(dataSnapshot.getKey());
                consulta.setNome(dataSnapshot.child("nome").getValue(String.class));
                consulta.setData(dataSnapshot.child("data").getValue(String.class));
                consulta.setHorario(dataSnapshot.child("horario").getValue(String.class));
                consulta.setMedico(dataSnapshot.child("medico").getValue(String.class));
                listaDeConsultas.add(consulta);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        queryRef.addChildEventListener(childEventListener);

    }

    @Override
    protected void onStop() {
        super.onStop();

        queryRef.removeEventListener(childEventListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Sair");
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.toString().equals("Sair")){
            FirebaseAuth.getInstance().signOut();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}



