package br.edu.bsi.projetoquizalgoritmo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import br.edu.bsi.projetoquizalgoritmo.Helper.ConfiguracaoFirebase;
import br.edu.bsi.projetoquizalgoritmo.Modelo.Pontos;
import br.edu.bsi.projetoquizalgoritmo.R;

public class Dificuldade extends AppCompatActivity {

    private Button btnFacil, btnModerado, btnDificil;
    private Boolean botaoModDesablitado = true;
    private Boolean botaoDifDesablitado = true;
    private FirebaseAuth auth;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dificuldade);

        btnFacil = findViewById(R.id.btnFacil);
        btnModerado = findViewById(R.id.btnModerado);
        btnDificil = findViewById(R.id.btnDificil);

        checarPontos();
        btnDificil.setBackgroundResource(R.drawable.botao_desabilitado);
        btnModerado.setBackgroundResource(R.drawable.botao_desabilitado);

        btnFacil.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent perguntaIntent = new Intent(Dificuldade.this, Quiz.class);
                perguntaIntent.putExtra("dificuldade","facil");
                startActivity(perguntaIntent);
                finish();
            }
        });
        btnModerado.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                if(botaoModDesablitado){

                    Toast.makeText(Dificuldade.this,"Complete modo Fácil para liberar esta fase!",Toast.LENGTH_SHORT).show();
                }else{
                    btnModerado.setBackgroundResource(R.drawable.botao_menu_principal);
                    Intent perguntaIntent = new Intent(Dificuldade.this, Quiz.class);
                    perguntaIntent.putExtra("dificuldade","moderado");
                    startActivity(perguntaIntent);
                    finish();
                }
            }
        });
        btnDificil.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(botaoDifDesablitado){

                    Toast.makeText(Dificuldade.this,"Complete modo Moderado para liberar esta fase!",Toast.LENGTH_SHORT).show();
                }else{

                    Intent perguntaIntent = new Intent(Dificuldade.this, Quiz.class);
                    perguntaIntent.putExtra("dificuldade","dificil");
                    startActivity(perguntaIntent);
                    finish();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
       Intent intent = new Intent(Dificuldade.this, MainActivity.class);
       startActivity(intent);
       finish();
    }

    void checarPontos(){
        auth = ConfiguracaoFirebase.getReferenciaAutenticacao();
        String id = auth.getCurrentUser().getUid().toString();

        DatabaseReference usuarioRef = referencia.child("usuarios")
                .child(id)
                .child("pontos");

        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Pontos p = snapshot.getValue(Pontos.class);
                if(p.getFacil() == 10){
                    btnModerado.setBackgroundResource(R.drawable.botao_menu_principal);
                    botaoModDesablitado = false;
                }else if(p.getModerado() == 10){
                    btnDificil.setBackgroundResource(R.drawable.botao_menu_principal);
                    botaoDifDesablitado = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}