package br.edu.bsi.projetoquizalgoritmo.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.edu.bsi.projetoquizalgoritmo.R;

public class Resultado extends AppCompatActivity {

    private TextView txtPontuacao, txtMensagem, txtCondicao;
    private Button btnVoltar;
    private ImageView imgResultado;
    private String modo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        Intent intent = getIntent();
        int ponto = (int)intent.getSerializableExtra("Pontos");
        modo = (String)intent.getSerializableExtra("Modo");

        inicializar();

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Resultado.this,Dificuldade.class);
                startActivity(intent);
                finish();
            }
        });

        if(modo.equals("Quiz")){
            checar(ponto,txtCondicao,txtPontuacao,txtMensagem,imgResultado);
        }else{
            mensagemSobrevivencia(ponto,txtCondicao,txtPontuacao,txtMensagem,imgResultado);
            btnVoltar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Resultado.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    void inicializar(){
        txtPontuacao = findViewById(R.id.txtPontuacao);
        txtMensagem = findViewById(R.id.txtMensagem);
        txtCondicao = findViewById(R.id.txtCondicao);
        btnVoltar = findViewById(R.id.btnVoltar);
        imgResultado = findViewById(R.id.imgResultado);
    }

    void mensagemSobrevivencia(int ponto, TextView condicao,TextView pontuacao, TextView mensagem,ImageView imagem){
        imagem.setImageResource(R.drawable.danger);

        condicao.setText("Sua pontua????o foi de :");

        pontuacao.setText(String.valueOf(ponto)+ " pontos");
        pontuacao.setTextColor(Color.GREEN);

        mensagem.setText("Continue tentando! eu sei que voc?? consegue mais");
    }

    void checar(int ponto, TextView condicao,TextView pontuacao, TextView mensagem,ImageView imagem){
        if(ponto == 10){

            imagem.setImageResource(R.drawable.trofeu);

            condicao.setText("Parabens !!!");

            pontuacao.setText(String.valueOf(ponto)+ " pontos");
            pontuacao.setTextColor(Color.GREEN);

            mensagem.setText("Voc?? ganhou uma MEDALHA por completar essa fase! Agora voc?? est?? apto para o proximo n??vel.");
        }else{
            imagem.setImageResource(R.drawable.failed);

            condicao.setText("Que pena !!!");

            pontuacao.setText(String.valueOf(ponto)+ " pontos");
            pontuacao.setTextColor(Color.RED);

            mensagem.setText("Voc?? n??o atingiu o n??vel necess??rio... Desanima n??o, tenta mais uma vez");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Resultado.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
