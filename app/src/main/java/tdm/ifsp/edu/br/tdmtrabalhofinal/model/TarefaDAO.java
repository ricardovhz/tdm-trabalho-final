package tdm.ifsp.edu.br.tdmtrabalhofinal.model;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TarefaDAO {
    private FirebaseFirestore db;

    public TarefaDAO() {
        this.db = FirebaseFirestore.getInstance();
    }

    public Task<Void> addTarefa(Tarefa tarefa) {
        return db.collection("tasks")
                .document(UUID.randomUUID().toString())
                .set(convert(tarefa));
    }

    public void getTarefas(final OnReturnListener listener) {
        db.collection("tasks")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        listener.onReturn(mapTarefas(task.getResult().getDocuments()));
                    }
                });
    }

    private List<Tarefa> mapTarefas(List<DocumentSnapshot> documents) {
        List<Tarefa> res = new ArrayList<Tarefa>();
        for (DocumentSnapshot snapshot : documents) {
            Tarefa t = snapshot.toObject(Tarefa.class);
            t.setId(snapshot.getId());
            res.add(t);
        }
        return res;
    }

    public void update(Tarefa tarefa) {
        db.collection("tasks")
                .document(tarefa.getId())
                .set(convert(tarefa));
    }

    public void changeListener(final OnReturnListener listener) {
        db.collection("tasks").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                listener.onReturn(mapTarefas(documentSnapshots.getDocuments()));
            }
        });
    }

    private Map<String, Object> convert(Tarefa tarefa) {
        Map<String, Object> result = new HashMap<>();
        result.put("description", tarefa.getDescription());
        result.put("dueDate", tarefa.getDueDate());
        result.put("done", tarefa.isDone());
        return result;
    }

    public interface OnReturnListener {
        void onReturn(List<Tarefa> tarefas);
    }

}
