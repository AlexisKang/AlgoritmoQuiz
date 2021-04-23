package br.edu.bsi.projetoquizalgoritmo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

import br.edu.bsi.projetoquizalgoritmo.Helper.ConfiguracaoFirebase;
import br.edu.bsi.projetoquizalgoritmo.R;

public class MainActivity extends AppCompatActivity {

    private Button btnJogar, btnSobrevivencia, btnConfiguracao, btnScore;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Configurando Toolbar
        Toolbar toolbar = findViewById(R.id.tlbPrincipal);
        toolbar.setTitle("Algoritmo Quiz");
        setSupportActionBar(toolbar);

        //Configuração de objetos
        auth = ConfiguracaoFirebase.getReferenciaAutenticacao();

        btnJogar = findViewById(R.id.btnJogar);
        btnSobrevivencia = findViewById(R.id.btnSobrevivencia);
        btnScore = findViewById(R.id.btnScore);
        btnConfiguracao = findViewById(R.id.btnConfiguracao);

        btnScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Score.class);
                startActivity(intent);
            }
        });

        btnSobrevivencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (MainActivity.this, Sobrevivencia.class);
                startActivity(intent);
            }
        });

        btnJogar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent dificuldadeIntent = new Intent(MainActivity.this, Dificuldade.class);
                startActivity(dificuldadeIntent);
            }
        });
        btnConfiguracao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                alert.setTitle("Aviso");
                alert.setMessage("Tela indisponível no momento");

                alert.setNeutralButton("Okay", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                    }
                });

                alert.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater =getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_sair:
                deslogarUsuario();
                startActivity(new Intent(getApplicationContext(),Login.class));
                break;
            case R.id.menu_medalhas:
                startActivity(new Intent(getApplicationContext(),Medalha.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deslogarUsuario(){
        try {
            auth.signOut();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}