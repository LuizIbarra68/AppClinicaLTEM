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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.inf3m171.ltem.appclinicaltem.model.Consulta;

public class MarcacaoConsultaActivity extends AppCompatActivity {

    private EditText etNomePaciente, etData;
    private Button btnData, btnSalvarConsulta;
    private Spinner spHorario, spMedico;
    private FirebaseDatabase database;
    private DatabaseReference reference;

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

    }

    private void selecionarData() {

        final AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        final DatePicker calendario = new DatePicker(this);
        alerta.setView(calendario);
        alerta.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String data = calendario.getDayOfMonth()+"/"+calendario.getMonth()+"/"+calendario.getYear();
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
            consulta.setNome(nome);

            reference.child("Consultas").push().setValue(consulta);
            Toast.makeText(this, "Consulta marcada com sucesso!", Toast.LENGTH_LONG);

            etNomePaciente.setText("");
            etData.setText("");
            spHorario.setSelection(0);
            spMedico.setSelection(0);
        }

    }

}
