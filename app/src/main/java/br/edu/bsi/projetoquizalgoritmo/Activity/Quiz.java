package br.edu.bsi.projetoquizalgoritmo.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import br.edu.bsi.projetoquizalgoritmo.Helper.ConfiguracaoFirebase;
import br.edu.bsi.projetoquizalgoritmo.Modelo.Pergunta;
import br.edu.bsi.projetoquizalgoritmo.Modelo.Pontos;
import br.edu.bsi.projetoquizalgoritmo.R;

public class Quiz extends AppCompatActivity {

    //Temporizador
    private static final long START_TIME_IN_MILLIS = 20000; //20sec
    private CountDownTimer MyCountDownTimer;
    private boolean TimerRunning;
    private long TimeLeftInMillis = START_TIME_IN_MILLIS;
    ProgressBar pbTempo;
    private TextView txtTempo;

    //Perguntas
    private Button btnResposta1, btnResposta2, btnResposta3, btnResposta4;
    private TextView txtPergunta, txtPerguntas, txtPontos;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    String dificuldade;
    int total= 1, ponto = 0;

    //Pontos
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Intent intent = getIntent();
        dificuldade = (String)intent.getSerializableExtra("dificuldade");

        btnResposta1 = findViewById(R.id.btnEntrar);
        btnResposta2 = findViewById(R.id.btnResposta2);
        btnResposta3 = findViewById(R.id.btnResposta3);
        btnResposta4 = findViewById(R.id.btnResposta4);

        txtPergunta = findViewById(R.id.txtPergunta);
        txtPerguntas = findViewById(R.id.txtPerguntas);
        txtPontos = findViewById(R.id.txtPontos);

        txtTempo = findViewById(R.id.txtTempo);
        pbTempo = findViewById(R.id.pbTempo);

        atualiza();
    }

    private void botoes(Pergunta pergunta, final Button botao){
        if(botao.getText().equals(pergunta.getResposta())){
            botao.setBackgroundResource(R.drawable.botao_acerto);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    botao.setBackgroundResource(R.drawable.botao_menu_principal);
                    ponto++;
                    total++;
                    atualiza();
                }
            },1500);
        }else{
            botao.setBackgroundResource(R.drawable.botao_erro);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    botao.setBackgroundResource(R.drawable.botao_menu_principal);
                    total++;
                    atualiza();
                }
            },1500);
        }
    }

    private void atualiza(){
        if(total > 10){
            addPontos();
            Intent intent = new Intent(Quiz.this, Resultado.class);
            intent.putExtra("Pontos", ponto);
            intent.putExtra("Modo","Quiz");
            startActivity(intent);
            finish();
        }else{
            DatabaseReference questao = referencia.child(dificuldade).child(String.valueOf(total));
            questao.addValueEventListener(new ValueEventListener() {
                @Override

                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    startTimer();
                    final Pergunta pergunta = snapshot.getValue(Pergunta.class);
                    txtPerguntas.setText(total+" / " + 10);
                    txtPontos.setText(String.valueOf(ponto));

                    txtPergunta.setText(pergunta.getPergunta());
                    btnResposta1.setText(pergunta.getOpcao1());
                    btnResposta2.setText(pergunta.getOpcao2());
                    btnResposta3.setText(pergunta.getOpcao3());
                    btnResposta4.setText(pergunta.getOpcao4());

                    btnResposta1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            resetTimer();
                            botoes(pergunta, btnResposta1);
                        }
                    });
                    btnResposta2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            resetTimer();
                            botoes(pergunta, btnResposta2);
                        }
                    });
                    btnResposta3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            resetTimer();
                            botoes(pergunta, btnResposta3);
                        }
                    });
                    btnResposta4.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            resetTimer();
                            botoes(pergunta, btnResposta4);
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Vc quer realmente sair?");
        // alert.setMessage("Message");

        alert.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Intent intent = new Intent(Quiz.this,Dificuldade.class);
                startActivity(intent);
                finish();
            }
        });

        alert.setNegativeButton("Não",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                });

        alert.show();
    }

    private void addPontos(){

        //Acessa o Usuario
        auth = ConfiguracaoFirebase.getReferenciaAutenticacao();
        String id = auth.getCurrentUser().getUid().toString();

        final DatabaseReference usuarioRef = referencia.child("usuarios")
                                              .child(id)
                                              .child("pontos");
        //Verifica a pontuação e add
        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Pontos p = snapshot.getValue(Pontos.class);

                if(dificuldade.equals("facil")){
                    if(p.getFacil()<ponto){
                        Map<String, Object> pontuacao = new HashMap<>();
                        pontuacao.put(dificuldade, ponto);
                        usuarioRef.updateChildren(pontuacao);
                    }
                }
                if(dificuldade.equals("moderado")){
                    if(p.getModerado()<ponto){
                        Map<String, Object> pontuacao = new HashMap<>();
                        pontuacao.put(dificuldade, ponto);
                        usuarioRef.updateChildren(pontuacao);
                    }
                }
                if(dificuldade.equals("dificl")){
                    if(p.getDificil()<ponto){
                        Map<String, Object> pontuacao = new HashMap<>();
                        pontuacao.put(dificuldade, ponto);
                        usuarioRef.updateChildren(pontuacao);
                    }
                }
                //Log.e("TAG", String.valueOf(snapshot));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void startTimer() {
        MyCountDownTimer = new CountDownTimer(START_TIME_IN_MILLIS, 1000) {

            long numberOfSeconds = START_TIME_IN_MILLIS/1000;
            long factor = 100/numberOfSeconds;
            @Override
            public void onTick(long millisUntilFinished) {

                TimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
                long secondsRemaining = (int) (millisUntilFinished / 1000);
                long progressPercentage = (numberOfSeconds-secondsRemaining) * factor ;
                pbTempo.setProgress((int) progressPercentage);
            }

            @Override
            public void onFinish() {
                total++;
                TimerRunning = false;
                txtTempo.setText("00:00");
                pbTempo.setProgress(100);
                atualiza();
            }
        }.start();

        TimerRunning = true;
    }

    private void pauseTimer() {
        MyCountDownTimer.cancel();
        TimerRunning = false;
        pbTempo.clearAnimation();
    }

    private void resetTimer() {
        pauseTimer();
        TimeLeftInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
        pbTempo.setProgress(0);
    }

    private void updateCountDownText() {
        int minutes = (int) (TimeLeftInMillis / 1000) / 60;
        int seconds = (int) (TimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        txtTempo.setText(timeLeftFormatted);
    }

}