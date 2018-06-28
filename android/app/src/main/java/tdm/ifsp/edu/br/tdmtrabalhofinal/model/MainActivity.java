package tdm.ifsp.edu.br.tdmtrabalhofinal.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tdm.ifsp.edu.br.tdmtrabalhofinal.Main2Activity;
import tdm.ifsp.edu.br.tdmtrabalhofinal.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private TarefaAdapter mAdapter;
    private TarefaDAO dao;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dao = new TarefaDAO();

        fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(this);

        mRecyclerView = findViewById(R.id.recycler);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new TarefaAdapter(new ArrayList<Tarefa>(), dao);
        mRecyclerView.setAdapter(mAdapter);


        dao.getTarefas(new TarefaDAO.OnReturnListener() {
            @Override
            public void onReturn(List<Tarefa> tarefas) {
                updateAdapter(tarefas);
            }
        });

        dao.changeListener(new TarefaDAO.OnReturnListener() {
            @Override
            public void onReturn(List<Tarefa> tarefas) {
                updateAdapter(tarefas);

            }
        });

    }

    private void updateAdapter(List<Tarefa> tarefas) {
        Log.i("t", "tarefas: " + tarefas.size());
        mAdapter.setTarefas(tarefas);
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, Main2Activity.class);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            Tarefa t = new Tarefa();

            t.setDescription(data.getStringExtra("description"));
            t.setDueDate((Date) data.getSerializableExtra("data"));

            dao.addTarefa(t);
            Toast.makeText(this, "Tarefa criada com sucesso!", Toast.LENGTH_SHORT).show();
        }
    }
}
