package com.inf3m171.ltem.appclinicaltem;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.inf3m171.ltem.appclinicaltem.model.Consulta;

public class MenuUsuarioActivity extends AppCompatActivity {

    private Button btnMarcarConsulta, btnListarConsultas;
    private TextView txtbemvindo;


    private Consulta consulta = new Consulta();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_usuario);

        btnMarcarConsulta = (Button) findViewById(R.id.btnMarcar);
        btnListarConsultas = (Button) findViewById(R.id.btnListar);

        txtbemvindo = (TextView)findViewById(R.id.txtola);


        btnMarcarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MenuUsuarioActivity.this, MarcacaoConsultaActivity.class);
                startActivity(i);
            }
        });

        btnListarConsultas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuUsuarioActivity.this, ListaConsultaActivity.class);
                startActivity(i);
            }
        });


    }

    private void mostrarNome(){
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

