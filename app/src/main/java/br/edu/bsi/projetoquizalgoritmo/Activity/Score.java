package br.edu.bsi.projetoquizalgoritmo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.edu.bsi.projetoquizalgoritmo.Helper.Adapter;
import br.edu.bsi.projetoquizalgoritmo.Helper.ConfiguracaoFirebase;
import br.edu.bsi.projetoquizalgoritmo.Modelo.Usuario;
import br.edu.bsi.projetoquizalgoritmo.R;

public class Score extends AppCompatActivity {

    private RecyclerView lista;
    List<Usuario> listaUsuario = new ArrayList<>();
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        //Configurando Toolbar
        Toolbar toolbar = findViewById(R.id.tlbPrincipal);
        toolbar.setTitle("Score");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lista = findViewById(R.id.listaUsuarios);

        carregarLista(lista);
    }


    void carregarLista(final RecyclerView lista){
        final DatabaseReference usuarioRef = referencia.child("usuarios");

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dados : snapshot.getChildren()) {

                    Usuario usuario = dados.getValue(Usuario.class);

                    listaUsuario.add(usuario);
                }
                Adapter adapter = new Adapter(listaUsuario);

                Collections.sort(listaUsuario, Usuario.Pontuacao);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                lista.setLayoutManager(layoutManager);
                lista.setHasFixedSize(true);
                lista.addItemDecoration(new DividerItemDecoration(Score.this,
                        LinearLayout.VERTICAL));

                lista.setAdapter(adapter);
                Log.d("TESTANDO", String.valueOf(lista));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Score.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
