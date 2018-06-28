package tdm.ifsp.edu.br.tdmtrabalhofinal.model;

import android.arch.core.util.Function;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import tdm.ifsp.edu.br.tdmtrabalhofinal.R;

public class TarefaAdapter extends RecyclerView.Adapter<TarefaAdapter.TarefaViewHolder> {

    private static final DateFormat FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    private List<Tarefa> tarefas;
    private TarefaDAO dao;

    public TarefaAdapter(List<Tarefa> tarefas, TarefaDAO dao) {
        this.tarefas = tarefas;
        this.dao = dao;
    }

    @NonNull
    @Override
    public TarefaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_tarefa, parent, false);

        final TarefaViewHolder holder = new TarefaViewHolder(v);

        holder.bind(new Runnable() {
            @Override
            public void run() {
                Log.i("oi", "check");
                tarefas.get(holder.position).setDone(holder.checkTarefa.isChecked());
                dao.update(tarefas.get(holder.position));
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TarefaViewHolder holder, final int position) {
        holder.position = position;
        holder.checkTarefa.setText(getText(tarefas.get(position)));
        holder.checkTarefa.setChecked(tarefas.get(position).isDone());
    }

    private String getText(Tarefa tarefa) {
        return new StringBuilder()
                .append(tarefa.getDescription())
                .append(" - para: ")
                .append(FORMAT.format(tarefa.getDueDate()))
                .append(" ")
                .append((tarefa.isDone() ? "(Completada)" : ""))
                .toString();
    }

    @Override
    public int getItemCount() {
        return tarefas.size();
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
        notifyDataSetChanged();
    }

    public static class TarefaViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkTarefa;
        public int position;

        public void bind(final Runnable listener) {
            this.checkTarefa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    listener.run();
                }
            });
        }

        public TarefaViewHolder(View itemView) {
            super(itemView);
            this.checkTarefa = itemView.findViewById(R.id.checkTarefa);
        }
    }
}
