package com.inf3m171.ltem.appclinicaltem;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.inf3m171.ltem.appclinicaltem.model.Consulta;

public class MarcacaoConsultaActivity extends AppCompatActivity {

    private EditText etNomePaciente, etData;
    private Button btnData, btnSalvarConsulta;
    private Spinner spHorario, spMedico;
    private FirebaseDatabase database;
    private DatabaseReference reference;


    private Query queryRef;
    private ChildEventListener childEventListener;

    private String acao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marcacao_consulta);

        etNomePaciente = (EditText) findViewById(R.id.etNomePaciente);
        etData = (EditText) findViewById(R.id.etDataConsulta);
        btnData = (Button) findViewById(R.id.btnSelecioanrData);
        btnSalvarConsulta = (Button) findViewById(R.id.btnSalvarConsulta);
        spHorario = (Spinner) findViewById(R.id.spHorario);
        spMedico = (Spinner) findViewById(R.id.spEspecialista);

        acao = getIntent().getExtras().getString("acao");

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selecionarData();

            }
        });

        btnSalvarConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             marcarConsulta();
            }
        });

        if (acao.equals("editar")){
            preencherFormulario();



        }

    }

    private void preencherFormulario() {


        etNomePaciente.setText( getIntent().getExtras().getString("nome"));
        String data = getIntent().getExtras().getString("data");

        String smes = data.substring(3,5);
        int mes = Integer.valueOf( smes) + 1;
        if (mes < 10)
            smes = "0" + mes;
        else
            smes = String.valueOf(mes);

        data = data.substring(0,3) + smes + data.substring(5,10);


        etData.setText(data);

        String[] medicos = getResources().getStringArray(R.array.arrayMedicos);
        for ( int i = 1; i < medicos.length;i++){
            if (medicos[i].equals(getIntent().getExtras().getString("medico") )){
                spMedico.setSelection(i);
                break;
            }
        }

        String[] horario = getResources().getStringArray(R.array.arrayHorarios);
        for ( int i = 1; i < horario.length;i++){
            if (horario[i].equals(getIntent().getExtras().getString("horario") )){
                spHorario.setSelection(i);
                break;
            }
        }
    }

    private void selecionarData() {

        final AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        final DatePicker calendario = new DatePicker(this);
        alerta.setView(calendario);
        alerta.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String data = calendario.getDayOfMonth()+"/"+(calendario.getMonth()+1)+"/"+calendario.getYear();
                etData.setText(data);

            }
        });

        alerta.setPositiveButton("Cancelar", null);

        alerta.show();

    }

    private void marcarConsulta(){
        String nome = etNomePaciente.getText().toString();
        String data = etData.getText().toString();
        spHorario.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String horario = spHorario.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        spMedico.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String medico = spMedico.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

        if (!nome.isEmpty() && !data.isEmpty()){

            Consulta consulta = new Consulta();
            String idUsuario = FirebaseAuth.getInstance().getCurrentUser().getUid();
            consulta.setNome(nome);
            consulta.setData(data);
            consulta.setIdUsuario(idUsuario);
            consulta.setHorario(spHorario.getSelectedItem().toString());
            consulta.setMedico(spMedico.getSelectedItem().toString());


            if (acao.equals("editar")){
                reference.child("Consultas").child( getIntent().getExtras().getString("id")  ).setValue(consulta);
            }else {
                reference.child("Consultas").push().setValue(consulta);
            }
            Toast.makeText(MarcacaoConsultaActivity.this, "Consulta marcada com sucesso!", Toast.LENGTH_LONG).show();

            etNomePaciente.setText("");
            etData.setText("");
            spHorario.setSelection(0);
            spMedico.setSelection(0);
        }

    }

}
