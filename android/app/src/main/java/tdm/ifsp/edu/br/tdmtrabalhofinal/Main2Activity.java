package tdm.ifsp.edu.br.tdmtrabalhofinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import tdm.ifsp.edu.br.tdmtrabalhofinal.model.Tarefa;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {


    private EditText edtDescricao;
    private EditText edtData;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        edtDescricao = findViewById(R.id.edtDescricao);
        edtData = findViewById(R.id.edtData);
        btnSalvar = findViewById(R.id.btnSalvar);
        btnSalvar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Log.i("salvar", "salvando tarefa");
        try {
            Intent i = new Intent();
            i.putExtra("description", edtDescricao.getText().toString());
            i.putExtra("data", df.parse(edtData.getText().toString()));
            setResult(1, i);
            finish();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
