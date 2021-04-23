package br.edu.bsi.projetoquizalgoritmo.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.bsi.projetoquizalgoritmo.Helper.AdapterMedalhas;
import br.edu.bsi.projetoquizalgoritmo.Helper.ConfiguracaoFirebase;
import br.edu.bsi.projetoquizalgoritmo.Modelo.Medalhas;

import br.edu.bsi.projetoquizalgoritmo.Modelo.Pontos;
import br.edu.bsi.projetoquizalgoritmo.R;

public class Medalha extends AppCompatActivity {

    private RecyclerView lista;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    List<Medalhas> listaMedalha= new ArrayList<>();
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medalha);

        Toolbar toolbar = findViewById(R.id.tlbPrincipal);
        toolbar.setTitle("Medalhas");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lista = findViewById(R.id.listaMedalhas);

        mostrarMedalhas(lista);
    }

    void mostrarMedalhas(final RecyclerView lista){
        auth = ConfiguracaoFirebase.getReferenciaAutenticacao();
        String id = auth.getCurrentUser().getUid().toString();
        final DatabaseReference usuarioRef = referencia.child("usuarios")
                .child(id)
                .child("pontos");

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Pontos p = snapshot.getValue(Pontos.class);
                if(p.getFacil()==10){
                    Medalhas bronze = new Medalhas("Bronze","Você adquiriu esta medalha ao completar com êxito o modo Facil", R.drawable.bronze);
                    listaMedalha.add(bronze);

                    if(p.getModerado()==10){
                        Medalhas prata = new Medalhas("Prata","Você adquiriu esta medalha ao completar com êxito o modo Moderado.",R.drawable.prata);
                        listaMedalha.add(prata);
                        if(p.getDificil()==10){
                            Medalhas ouro = new Medalhas("Ouro","Você adquiriu esta medalha ao completar com êxito o modo Dificil.",R.drawable.ouro);
                            listaMedalha.add(ouro);
                        }
                    }
                }

                AdapterMedalhas adapter = new AdapterMedalhas(listaMedalha);

                RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getApplicationContext());
                lista.setLayoutManager(layoutManager);
                lista.setHasFixedSize(true);
                lista.addItemDecoration(new DividerItemDecoration(Medalha.this,
                        LinearLayout.VERTICAL));
                lista.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}